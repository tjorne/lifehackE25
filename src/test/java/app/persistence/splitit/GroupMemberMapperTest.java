package app.persistence.splitit;

import app.entities.splitit.Group;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberMapperTest {
    private static ConnectionPool testPool;
    private GroupMemberMapper groupMemberMapper;

    @BeforeAll
    static void setUpDatabase() {
        testPool = ConnectionPool.getInstance(
                "postgres",
                "postgres",
                "jdbc:postgresql://localhost:5432/spiltit?currentSchema=test",
                "spiltit"
        );

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS test.expense CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.group_users CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.groups CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");

            stmt.execute("CREATE TABLE test.users (" +
                    "user_id SERIAL PRIMARY KEY," +
                    "username VARCHAR(100) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "role VARCHAR(20) DEFAULT 'user' NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            stmt.execute("CREATE TABLE test.groups (" +
                    "group_id SERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            stmt.execute("CREATE TABLE test.group_users (" +
                    "user_id INT NOT NULL," +
                    "group_id INT NOT NULL," +
                    "PRIMARY KEY (user_id, group_id)," +
                    "CONSTRAINT group_users_user_id_fkey FOREIGN KEY (user_id) REFERENCES test.users(user_id) ON DELETE CASCADE," +
                    "CONSTRAINT group_users_group_id_fkey FOREIGN KEY (group_id) REFERENCES test.groups(group_id) ON DELETE CASCADE" +
                    ")");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        groupMemberMapper = new GroupMemberMapper(testPool);

        try (Connection con = testPool.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("DELETE FROM test.group_users");
            stmt.execute("DELETE FROM test.groups");
            stmt.execute("DELETE FROM test.users");

            stmt.execute("ALTER SEQUENCE test.users_user_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE test.groups_group_id_seq RESTART WITH 1");

            stmt.execute("INSERT INTO test.users (username, password, role) VALUES " +
                    "('Alice', 'pass123', 'user'), " +
                    "('Bob', 'pass456', 'user'), " +
                    "('Charlie', 'pass789', 'user'), " +
                    "('David', 'pass000', 'user'), " +
                    "('Eve', 'pass111', 'user')");

            stmt.execute("INSERT INTO test.groups (name) VALUES " +
                    "('Weekendtur'), " +
                    "('Fredagsbar'), " +
                    "('Fødselsdagsfest'), " +
                    "('Tom gruppe')");

            stmt.execute("INSERT INTO test.group_users (user_id, group_id) VALUES " +
                    "(1, 1), " +
                    "(2, 1), " +
                    "(3, 1), " +
                    "(1, 2), " +
                    "(4, 2), " +
                    "(5, 3)");

        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(testPool.getConnection());
    }

    @Test
    void testAddMemberToGroup() throws DatabaseException {
        boolean result = groupMemberMapper.addMemberToGroup(2, 2);
        assertTrue(result);

        boolean isMember = groupMemberMapper.isMemberOfGroup(2, 2);
        assertTrue(isMember);
    }

    @Test
    void testAddMemberToGroupAlreadyExists() {
        assertThrows(DatabaseException.class, () -> {
            groupMemberMapper.addMemberToGroup(1, 1);
        });
    }


    @Test
    void testRemoveMemberFromGroup() throws DatabaseException {
        boolean result = groupMemberMapper.removeMemberFromGroup(1, 1);
        assertTrue(result);

        boolean isMember = groupMemberMapper.isMemberOfGroup(1, 1);
        assertFalse(isMember);
    }


    @Test
    void testGetUsersByGroupId() throws DatabaseException {
        List<User> users = groupMemberMapper.getUsersByGroupId(1);
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    void testGetUsersByGroupIdEmptyGroup() throws DatabaseException {
        List<User> users = groupMemberMapper.getUsersByGroupId(4);
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }


    @Test
    void testGetGroupsByUserId() throws DatabaseException {
        List<Group> groups = groupMemberMapper.getGroupsByUserId(1);
        assertNotNull(groups);
        assertEquals(2, groups.size());

    }

    @Test
    void testRemoveAllMembersFromGroup() throws DatabaseException {
        boolean result = groupMemberMapper.removeAllMembersFromGroup(1);
        assertTrue(result);

        List<User> users = groupMemberMapper.getUsersByGroupId(1);
        assertTrue(users.isEmpty());
    }

    @Test
    void testRemoveUserFromAllGroups() throws DatabaseException {
        boolean result = groupMemberMapper.removeUserFromAllGroups(1);
        assertTrue(result);

        List<Group> groups = groupMemberMapper.getGroupsByUserId(1);
        assertTrue(groups.isEmpty());

        List<User> usersInGroup1 = groupMemberMapper.getUsersByGroupId(1);
        assertEquals(2, usersInGroup1.size());
    }

}