package app.services.splitit;

import app.dto.UserBalanceDTO;
import app.entities.User;
import app.entities.splitit.Expense;
import app.entities.splitit.Settlement;
import app.exceptions.DatabaseException;
import app.persistence.splitit.ExpenseMapper;
import app.persistence.splitit.GroupMemberMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BalanceServiceImpl implements BalanceService {
    private ExpenseMapper expenseMapper;
    private GroupMemberMapper groupMemberMapper;

    public BalanceServiceImpl(ExpenseMapper expenseMapper, GroupMemberMapper groupMemberMapper) {
        this.expenseMapper = expenseMapper;
        this.groupMemberMapper = groupMemberMapper;
    }

    @Override
    public List<UserBalanceDTO> getGroupBalances(int groupId) throws DatabaseException {
        List<Expense> expenses = expenseMapper.getExpensesByGroupId(groupId);
        List<User> users = groupMemberMapper.getUsersByGroupId(groupId);
        return calculateUserBalances(expenses,users);
    }

    @Override
    public List<Settlement> getSettlements(int groupId) throws DatabaseException {
        List<UserBalanceDTO> balanceDTOS = getGroupBalances(groupId);
        return calculateSettlements(balanceDTOS);
    }

    public List<UserBalanceDTO> calculateUserBalances(List<Expense> expenses, List<User> users)
    {
        Map<String, Double> paidByUser = users.stream()
                .collect(Collectors.toMap(
                        User::getUserName,
                        user -> expenses.stream()
                                .filter(e -> e.getUserId() == user.getUserId())
                                .mapToDouble(Expense::getAmount)
                                .sum()
                ));
        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double sharePerUser = total / users.size();

        List<UserBalanceDTO> userBalanceDTOS = new ArrayList<>();

        for (User user : users) {
            String username = user.getUserName();
            double paid = paidByUser.get(username);
            double balance = Math.round((paid - sharePerUser) * 100.0) / 100.0;
            userBalanceDTOS.add(new UserBalanceDTO(username, balance));
        }
        return userBalanceDTOS;
    }

    public List<Settlement> calculateSettlements(List<UserBalanceDTO> balanceDTOS)
    {
        List<Settlement> settlements = new ArrayList<>();

        List<UserBalanceDTO> debitors = balanceDTOS.stream()
                .filter(balance -> balance.getBalance() < 0)
                .collect(Collectors.toList());

        List<UserBalanceDTO> creditors = balanceDTOS.stream()
                .filter(balance -> balance.getBalance() > 0)
                .collect(Collectors.toList());

        for(UserBalanceDTO debitor: debitors)
        {
            double amountOwed = -debitor.getBalance();

            for(UserBalanceDTO creditor: creditors)
            {
                if(amountOwed <= 0){
                    break;
                }
                if(creditor.getBalance() <=0){
                    continue;
                }

                double payment = Math.min(amountOwed, creditor.getBalance());

                settlements.add(new Settlement(
                        debitor.getUserName(),
                        creditor.getUserName(),
                        payment
                ));

                amountOwed -= payment;
                creditor.setBalance(creditor.getBalance() - payment);
            }
        }
        return settlements;
    }
}


