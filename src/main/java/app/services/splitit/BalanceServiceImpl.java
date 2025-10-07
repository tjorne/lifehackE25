package app.services.splitit;

import app.dto.UserBalanceDTO;
import app.entities.User;
import app.entities.splitit.Expense;
import app.entities.splitit.Settlement;
import app.exceptions.DatabaseException;
import app.persistence.splitit.ExpenseMapper;
import app.persistence.splitit.GroupMemberMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BalanceServiceImpl implements BalanceService{
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


        for(User user: users){
            String username = user.getUserName();
            double paid = paidByUser.get(username);
            double balance = Math.round((paid - sharePerUser) * 100.0) / 100.0;
            userBalanceDTOS.add(new UserBalanceDTO(username,balance));
        }
        return userBalanceDTOS;
    }

    @Override
    public List<Settlement> getSettlements(int groupId) {
        return List.of();
    }
}
