package app.services.splitit;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.splitit.ExpenseMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceImplTest {

        private ExpenseServiceImpl expenseService;

        @BeforeEach
        void setUp() {
            expenseService = new ExpenseServiceImpl(null,null);
        }

        @Test
        void createExpenseWithEmptyDescription() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                expenseService.createExpense(1, 1, "", 100);
            });
            assertEquals("Description cannot be empty", ex.getMessage());
        }

        @Test
        void createExpenseWithNegativeAmount() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                expenseService.createExpense(1, 1, "Pizza", -50);
            });
            assertEquals("Amount cannot be below zero", ex.getMessage());
        }
    }
