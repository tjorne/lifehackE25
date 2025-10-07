package app.services.splitit;

import app.entities.splitit.Group;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.util.List;

public interface AccountService {
    //users
    List<User> getAllUsers() throws DatabaseException;
    User getUserById(int userId) throws DatabaseException;

    //groups
    Group createGroup(String name) throws DatabaseException;
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
