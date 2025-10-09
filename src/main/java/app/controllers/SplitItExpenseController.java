package app.controllers;

import app.dto.ExpenseDTO;
import app.entities.User;
import app.entities.splitit.CurrencyFormatter;
import app.entities.splitit.Expense;
import app.entities.splitit.Group;
import app.entities.splitit.Settlement;
import app.exceptions.DatabaseException;
import app.services.splitit.AccountService;
import app.services.splitit.BalanceService;
import app.services.splitit.ExpenseService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SplitItExpenseController {
    private ExpenseService expenseService;
    private BalanceService balanceService;
    private AccountService accountService;

    public SplitItExpenseController(ExpenseService expenseService, BalanceService balanceService,AccountService accountService)
    {
        this.expenseService = expenseService;
        this.balanceService = balanceService;
        this.accountService = accountService;
    }

    public void addRoutes(Javalin app)
    {
        app.get("/expense", ctx -> showGroupExpenses(ctx));
        app.get("/backToMain", ctx -> backToMain(ctx));
        app.get("/result", ctx -> showResult(ctx));
        app.get("/expense/edit", ctx -> showEditExpensePage(ctx));


        app.post("/expenses/addExpense", ctx -> addExpense(ctx));
        app.post("/deleteGroup", ctx -> deleteGroup(ctx));
        app.post("/expenses/deleteExpense", ctx -> deleteExpense(ctx));
        app.post("/expenses/updateExpense",ctx -> updateExpense(ctx));
        app.post("/expenses/editExpense", ctx -> editExpense(ctx));
    }

    private void showEditExpensePage(Context ctx)
    {
        int groupId = ctx.sessionAttribute("groupId");
        ExpenseDTO expenseDTO = ctx.sessionAttribute("editExpense");
        showGroupExpenses(ctx);

        ctx.attribute("editExpense", expenseDTO);

        ctx.render("/splitit/expenses.html");
    }

    private void updateExpense(Context ctx)
    {
        {
            ExpenseDTO expenseDTO = ctx.sessionAttribute("editExpense");
            int groupId = ctx.sessionAttribute("groupId");
            String description = ctx.formParam("description");
            double amount = 0.0;

            try {
                amount = Double.parseDouble(ctx.formParam("amount"));
            } catch (NumberFormatException e){
                ctx.sessionAttribute("errorMessage","Please only input numbers in amount");
                ctx.redirect("/expense?groupId=" + groupId);
                return;
            }

            try {
                if(expenseService.updateExpense(expenseDTO.getExpenseId(),description,amount))
                {
                    List<ExpenseDTO> expenses = ctx.sessionAttribute("expenses");

                    if (expenses == null) {
                        expenses = new ArrayList<>();
                    } else {
                        expenses = new ArrayList<>(expenses);
                    }
                    expenses.add(expenseDTO);
                    ctx.sessionAttribute("expenses", expenses);
                }

            } catch (DatabaseException e) {
                ctx.sessionAttribute("errorMessage", e.getMessage());
            }
            ctx.redirect("/expense?groupId=" + groupId);
        }

    }

    private void editExpense(Context ctx)
    {
        int groupId = ctx.sessionAttribute("groupId");
        try {
            int expenseId = Integer.parseInt(ctx.formParam("expenseId"));
            ExpenseDTO expenseDTO = expenseService.getExpenseById(expenseId);
            ctx.sessionAttribute("editExpense",expenseDTO);
            ctx.redirect("/expense/edit?groupId=" + groupId);
        } catch (Exception e){
            ctx.sessionAttribute("errorMessage","Error while trying to edit.");
            ctx.redirect("/expense?groupId=" + groupId);
        }
    }

    private void deleteExpense(Context ctx)
    {
        int groupId = ctx.sessionAttribute("groupId");
        try {

            int expenseId = Integer.parseInt(ctx.formParam("expenseId"));
            expenseService.deleteExpense(expenseId);
            ctx.redirect("/expense?groupId=" + groupId);

        } catch (NumberFormatException e) {
            ctx.attribute("errorMessage", "Ugyldigt expense ID.");
            ctx.redirect("/expense");
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", e.getMessage());
            ctx.redirect("/expense");
        }
    }

    private void deleteGroup(Context ctx)
    {
        int groupId = ctx.sessionAttribute("groupId");

        try{
            expenseService.deleteAllExpensesByGroupId(groupId);
            accountService.removeAllMembersFromGroup(groupId);
            accountService.deleteGroup(groupId);
            ctx.redirect("/splitit");
        }catch (DatabaseException e){
            ctx.attribute("errorMessage",e.getMessage());
            ctx.redirect("/expense?groupId=" + groupId);
        }
    }

    private void showResult(Context ctx)
    {
        int groupId = ctx.sessionAttribute("groupId");

        System.out.println(groupId);
        List<Settlement> settlements = new ArrayList<>();
        try{
            Group group = accountService.getGroupById(groupId);
            settlements = balanceService.getSettlements(groupId);
            ctx.attribute("groupName",group.getName());
            ctx.attribute("settlements",settlements);
        } catch (DatabaseException e){
            ctx.attribute("errorMessage",e.getMessage());
            ctx.redirect("/result");
        }
        ctx.render("/splitit/result");

    }

    private void backToMain(Context ctx)
    {
        ctx.redirect("/splitit/");
    }

    private void addExpense(Context ctx)
    {
            User user = ctx.sessionAttribute("currentUser");
            int groupId = ctx.sessionAttribute("groupId");
            String description = ctx.formParam("description");
            double amount = 0.0;

            try {
                 amount = Double.parseDouble(ctx.formParam("amount"));
            } catch (NumberFormatException e){
                ctx.sessionAttribute("errorMessage","Please only input numbers in amount");
                ctx.redirect("/expense?groupId=" + groupId);
                return;
            }

            try {
                Expense expense = expenseService.createExpense(user.getUserId(), groupId,description,amount);
                ExpenseDTO expenseDTO = expenseService.getExpenseById(expense.getExpenseId());

                List<ExpenseDTO> expenses = ctx.sessionAttribute("expenses");

                if (expenses == null) {
                    expenses = new ArrayList<>();
                } else {
                    expenses = new ArrayList<>(expenses);
                }
                expenses.add(expenseDTO);
                ctx.sessionAttribute("expenses", expenses);

            } catch (DatabaseException e) {
                ctx.sessionAttribute("errorMessage", e.getMessage());
            }
            ctx.redirect("/expense?groupId=" + groupId);

        }

    private void showGroupExpenses(Context ctx)
    {
        String groupIdParam = ctx.queryParam("groupId");
        User user = ctx.sessionAttribute("currentUser");
        ctx.attribute("accountname",user.getUserName());
        ctx.attribute("currentUser",user);

        String errorMessage = ctx.sessionAttribute("errorMessage");
        if (errorMessage != null) {
            ctx.attribute("errorMessage", errorMessage);
            ctx.consumeSessionAttribute("errorMessage");
        }

        if (groupIdParam == null) {
            ctx.sessionAttribute("errorMessage", "No group selected");
            ctx.redirect("/splitit");
            return;
        }
        int groupId = Integer.parseInt(groupIdParam);
        ctx.sessionAttribute("groupId", groupId);

        try {

            Group group = accountService.getGroupById(groupId);
            List<User> members = accountService.getGroupMembers(groupId);

            List<ExpenseDTO> groupExpenses = expenseService.getExpensesByGroupId(groupId);
            List<ExpenseDTO> userExpenses = expenseService.getExpensesByUserAndGroup(user.getUserId(),group.getGroupId());
            double groupTotal = 0.0;
            double userTotal = 0.0;

            if (groupExpenses == null) {
                groupExpenses = new ArrayList<>();
            }

            if(!userExpenses.isEmpty())
            {
                userTotal = calculateTotal(userExpenses);
            }

            if(!groupExpenses.isEmpty())
            {
                groupTotal = calculateTotal(groupExpenses);
            }

            String userTotalFormated = CurrencyFormatter.format(userTotal);
            String groupTotalFormated = CurrencyFormatter.format(groupTotal);

            ctx.attribute("groupName",group.getName());
            ctx.attribute("members", members);
            ctx.attribute("expenses", groupExpenses);
            ctx.attribute("userTotal", userTotalFormated);
            ctx.attribute("groupTotal", groupTotalFormated);

            ctx.sessionAttribute("expenses", groupExpenses);

        } catch (DatabaseException e) {
            ctx.sessionAttribute("errorMessage", e.getMessage());
            ctx.redirect("/splitit");
            return;
        }
        ctx.render("/splitit/expenses.html");
    }

    private double calculateTotal(List<ExpenseDTO> expenseDTOS)
    {
        return expenseDTOS.stream()
                    .mapToDouble(ExpenseDTO::getAmount)
                    .sum();
    }
}
