package app.services.splitit;

import app.dto.UserBalanceDTO;
import app.entities.User;
import app.entities.splitit.Expense;
import app.entities.splitit.Settlement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceImplTest {
    private BalanceServiceImpl service = new BalanceServiceImpl(null,null);

    @Test
    void testCalculateTwoUserBalances()
    {
        List<User> users = List.of(
                new User(1, "Alice", "alice@test.com", "pass"),
                new User(2, "Bob", "bob@test.com", "pass")
        );

        List<Expense> expenses = List.of(
                new Expense(1, 1, 1, "Dinner", 100.0, null),
                new Expense(2, 2, 1, "Drinks", 200.0, null)
        );

        List<UserBalanceDTO> result = service.calculateUserBalances(expenses, users);

        UserBalanceDTO bob = result.stream()
                .filter(balance -> balance.getUserName().equals("Bob"))
                .findFirst()
                .orElseThrow();

        UserBalanceDTO alice = result.stream()
                .filter(balance -> balance.getUserName().equals("Alice"))
                .findFirst()
                .orElseThrow();

        assertEquals(2, result.size());
        assertEquals(-50.0,alice.getBalance(),0.01);
        assertEquals(50.0,bob.getBalance(),0.01);
    }

    @Test
    void testCalculateThreeUserBalances()
    {
            BalanceServiceImpl service = new BalanceServiceImpl(null, null);

            List<User> users = List.of(
                    new User(1, "Alice", "alice@test.com", "pass"),
                    new User(2, "Bob", "bob@test.com", "pass"),
                    new User(3, "Charlie", "charlie@test.com", "pass")
            );

            List<Expense> expenses = List.of(
                    new Expense(1, 1, 1, "Dinner", 300.0, null)  // Kun Alice betaler alt
            );

            List<UserBalanceDTO> result = service.calculateUserBalances(expenses, users);

            assertEquals(3, result.size());

        UserBalanceDTO bob = result.stream()
                .filter(balance -> balance.getUserName().equals("Bob"))
                .findFirst()
                .orElseThrow();

        UserBalanceDTO alice = result.stream()
                .filter(balance -> balance.getUserName().equals("Alice"))
                .findFirst()
                .orElseThrow();

        UserBalanceDTO charlie = result.stream()
                .filter(balance -> balance.getUserName().equals("Charlie"))
                .findFirst()
                .orElseThrow();

            assertEquals(200.0,alice.getBalance(), 0.01);
            assertEquals(-100.0, bob.getBalance(), 0.01);
            assertEquals(-100.0, charlie.getBalance(), 0.01);
    }

    @Test
    void testCalculateSettlementsOnePaidAll()
    {
        // Arrange
        List<UserBalanceDTO> balances = List.of(
                new UserBalanceDTO("Alice", 100.0),
                new UserBalanceDTO("Bob", -50.0),
                new UserBalanceDTO("Charlie", -50.0)
        );

        List<Settlement> settlements = service.calculateSettlements(balances);

        assertEquals(2, settlements.size());

        double totalSettlements = settlements.stream()
                .mapToDouble(Settlement::getAmount)
                .sum();
        assertEquals(100.0, totalSettlements, 0.01);

        double totalToAlice = settlements.stream()
                .filter(s -> s.getToUserName().equals("Alice"))
                .mapToDouble(Settlement::getAmount)
                .sum();
        assertEquals(100.0, totalToAlice, 0.01);

        assertEquals(50.0, getTotalPaidBy(settlements, "Bob"), 0.01);
        assertEquals(50.0, getTotalPaidBy(settlements, "Charlie"), 0.01);
    }

    @Test
    void testCalculateSettlementsWithTwoCreditors()
    {

        List<UserBalanceDTO> balances = List.of(
                new UserBalanceDTO("Alice", 60.0),
                new UserBalanceDTO("Bob", 40.0),
                new UserBalanceDTO("Charlie", -100.0)
        );

        List<Settlement> settlements = service.calculateSettlements(balances);

        assertEquals(2, settlements.size());
        assertEquals(100.0, getTotalPaidBy(settlements, "Charlie"), 0.01);

        Settlement toAlice = settlements.stream()
                .filter(s -> s.getFromUserName().equals("Charlie") && s.getToUserName().equals("Alice"))
                .findFirst()
                .orElseThrow();
        assertEquals(60.0, toAlice.getAmount(), 0.01);

        Settlement toBob = settlements.stream()
                .filter(s -> s.getFromUserName().equals("Charlie") && s.getToUserName().equals("Bob"))
                .findFirst()
                .orElseThrow();
        assertEquals(40.0, toBob.getAmount(), 0.01);
    }

    @Test
    void testCalculateSettlementsWithThreeDebitors()
    {
        // Arrange
        List<UserBalanceDTO> balances = List.of(
                new UserBalanceDTO("Alice", 150.0),
                new UserBalanceDTO("Bob", -50.0),
                new UserBalanceDTO("Charlie", -30.0),
                new UserBalanceDTO("David", -70.0)
        );

        List<Settlement> settlements = service.calculateSettlements(balances);

        double totalSettlements = settlements.stream()
                .mapToDouble(Settlement::getAmount)
                .sum();
        assertEquals(150.0, totalSettlements, 0.01);

        assertEquals(50.0, getTotalPaidBy(settlements, "Bob"), 0.01);
        assertEquals(30.0, getTotalPaidBy(settlements, "Charlie"), 0.01);
        assertEquals(70.0, getTotalPaidBy(settlements, "David"), 0.01);

        double aliceReceives = settlements.stream()
                .filter(s -> s.getToUserName().equals("Alice"))
                .mapToDouble(Settlement::getAmount)
                .sum();
        assertEquals(150.0, aliceReceives, 0.01);
    }

    private double getTotalPaidBy(List<Settlement> settlements, String fromUser) {
        return settlements.stream()
                .filter(s -> s.getFromUserName().equals(fromUser))
                .mapToDouble(Settlement::getAmount)
                .sum();
    }
}