package app.services.splitit;

import app.entities.Group;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.util.List;

public interface AccountService {
    //users
    User login(String name, String password) throws DatabaseException;
    User createUser(String name, String password) throws DatabaseException;
    User getUserById(int userId) throws DatabaseException;
    boolean updateUser(User user) throws DatabaseException;
    boolean deleteUser(int userId) throws DatabaseException;

    //groups
    Group createGroup(String name, int creatorUserId) throws DatabaseException;
    Group getGroupById(int groupId) throws DatabaseException;
    List<Group> getAllGroups() throws DatabaseException;
    boolean updateGroup(Group group) throws DatabaseException;
    boolean deleteGroup(int groupId) throws DatabaseException;

    //group_members
    boolean addMemberToGroup(int userId, int groupId) throws DatabaseException;
    boolean removeMemberFromGroup(int userId, int groupId) throws DatabaseException;
    List<User> getGroupMembers(int groupId) throws DatabaseException;
    List<Group> getUserGroups(int userId) throws DatabaseException;
}
