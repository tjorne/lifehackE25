package app.services.splitit;

import app.entities.User;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;
import app.persistence.splitit.GroupMapper;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    GroupMapper groupMapper;

    public AccountServiceImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException
    {
        //List<User> users = UserMapper.
        return List.of();
    }

    @Override
    public User getUserById(int userId) throws DatabaseException {
        return null;
    }

    @Override
    public Group createGroup(String name) throws DatabaseException {
        //TODO Validate name
        return groupMapper.createGroup(name);

    }

    @Override
    public Group getGroupById(int groupId) throws DatabaseException {
        Group group = groupMapper.getGroupById(groupId);
        return group;
    }

    @Override
    public List<Group> getAllGroups() throws DatabaseException {
        List<Group> groups = groupMapper.getAllGroups();
        return groups;
    }

    @Override
    public boolean updateGroup(Group group) throws DatabaseException {
        return false;
    }

    @Override
    public boolean deleteGroup(int groupId) throws DatabaseException {
        return false;
    }

    @Override
    public boolean addMemberToGroup(int userId, int groupId) throws DatabaseException {
        return false;
    }

    @Override
    public boolean removeMemberFromGroup(int userId, int groupId) throws DatabaseException {
        return false;
    }

    @Override
    public List<User> getGroupMembers(int groupId) throws DatabaseException {
        return List.of();
    }

    @Override
    public List<Group> getUserGroups(int userId) throws DatabaseException {
        return List.of();
    }
}
