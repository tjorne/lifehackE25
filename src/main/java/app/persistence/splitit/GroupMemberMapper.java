package app.persistence.splitit;

import app.entities.splitit.Group;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberMapper {
    private ConnectionPool connectionPool;

    public GroupMemberMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean addMemberToGroup(int userId, int groupId) throws DatabaseException {
        String sql = "INSERT INTO group_users (user_id, group_id) VALUES (?, ?)";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                result = true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DatabaseException("Bruger er allerede medlem af gruppen");

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke tilføje medlem til gruppe: " + e.getMessage());
        }
        return result;
    }

    public boolean removeMemberFromGroup(int userId, int groupId) throws DatabaseException {
        String sql = "DELETE FROM group_users WHERE user_id = ? AND group_id = ?";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke fjerne medlem fra gruppe: " + e.getMessage());
        }
        return result;
    }

    public List<User> getUsersByGroupId(int groupId) throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.password, u.role " +
                "FROM users u " +
                "JOIN group_users gu ON u.user_id = gu.user_id " +
                "WHERE gu.group_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                users.add(new User(userId, name, password,role));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af brugere for gruppe " + groupId + ": " + e.getMessage());
        }
        return users;
    }

    public List<Group> getGroupsByUserId(int userId) throws DatabaseException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT g.group_id, g.name " +
                "FROM groups g " +
                "JOIN group_users gu ON g.group_id = gu.group_id " +
                "WHERE gu.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int groupId = rs.getInt("group_id");
                String name = rs.getString("name");

                groups.add(new Group(groupId, name));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af grupper for bruger " + userId + ": " + e.getMessage());
        }
        return groups;
    }

    public boolean isMemberOfGroup(int userId, int groupId) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM group_users WHERE user_id = ? AND group_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved tjek af gruppemedlemskab: " + e.getMessage());
        }
        return false;
    }


    public boolean removeAllMembersFromGroup(int groupId) throws DatabaseException {
        String sql = "DELETE FROM group_users WHERE group_id = ?";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            ps.executeUpdate();
            result = true;

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke fjerne medlemmer fra gruppe: " + e.getMessage());
        }
        return result;
    }

    public boolean removeUserFromAllGroups(int userId) throws DatabaseException {
        String sql = "DELETE FROM group_users WHERE user_id = ?";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
            result = true;

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke fjerne bruger fra grupper: " + e.getMessage());
        }
        return result;
    }
}


