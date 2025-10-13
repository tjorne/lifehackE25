package app.services.splitit;

import app.entities.User;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;
import app.persistence.splitit.GroupMapper;
import app.persistence.splitit.GroupMemberMapper;

import java.util.Comparator;
import java.util.List;

public class AccountServiceImpl implements AccountService{
    GroupMapper groupMapper;
    GroupMemberMapper groupMemberMapper;

    public AccountServiceImpl(GroupMapper groupMapper, GroupMemberMapper groupMemberMapper) {
        this.groupMapper = groupMapper;
        this.groupMemberMapper = groupMemberMapper;
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException
    {
        List<User> users = UserMapper.getAllUsers();
        users.stream().sorted(Comparator.comparing(User::getUserName));
        return List.copyOf(users);
    }

    @Override
    public User getUserById(int userId) throws DatabaseException {
        User user = UserMapper.getUserById(userId);
        if (user == null) {
            throw new DatabaseException("User not found with id: " + userId);
        }
        return user;
    }

    @Override
    public Group createGroup(String name) throws DatabaseException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be empty");
        }
        return groupMapper.createGroup(name);

    }

    @Override
    public Group getGroupById(int groupId) throws DatabaseException {
        Group group = groupMapper.getGroupById(groupId);
        if (group == null) {
            throw new DatabaseException("Group not found with id: " + groupId);
        }
        return group;
    }

    @Override
    public List<Group> getAllGroups() throws DatabaseException {
        List<Group> groups = groupMapper.getAllGroups();
        groups.sort(Comparator.comparing(Group::getName));
        return List.copyOf(groups);
    }

    @Override
    public boolean updateGroup(Group group) throws DatabaseException {
        if (group == null || group.getGroupId() <= 0) {
            throw new IllegalArgumentException("Invalid group");
        }
        return groupMapper.updateGroup(group);
    }

    @Override
    public boolean deleteGroup(int groupId) throws DatabaseException {
        Group group = groupMapper.getGroupById(groupId);
        if (group == null) {
            return false;
        }
        return groupMapper.deleteGroup(groupId);
    }

    @Override
    public boolean addMemberToGroup(int userId, int groupId) throws DatabaseException {
        User user = UserMapper.getUserById(userId);
        Group group = groupMapper.getGroupById(groupId);
        if (user == null || group == null) {
            return false;
        }
        return groupMemberMapper.addMemberToGroup(userId,groupId);
    }

    @Override
    public boolean removeAllMembersFromGroup(int groupId) throws DatabaseException {
        return groupMemberMapper.removeAllMembersFromGroup(groupId);
    }

    @Override
    public boolean removeMemberFromGroup(int userId, int groupId) throws DatabaseException {
        return groupMemberMapper.removeMemberFromGroup(userId, groupId);
    }

    @Override
    public List<User> getGroupMembers(int groupId) throws DatabaseException {
        Group group = groupMapper.getGroupById(groupId);
        if (group == null) {
            throw new DatabaseException("Group not found");
        }
        List<User> members = groupMemberMapper.getUsersByGroupId(groupId);
        members.sort(Comparator.comparing(User::getUserName));
        return List.copyOf(members);
    }

    @Override
    public List<Group> getUserGroups(int userId) throws DatabaseException {
        User user = UserMapper.getUserById(userId);
        if (user == null) {
            throw new DatabaseException("User not found");
        }
        List<Group> groups = groupMemberMapper.getGroupsByUserId(userId);
        groups.sort(Comparator.comparing(Group::getName));
        return List.copyOf(groups);
    }

    public boolean isUserInGroup(List<User> members, int userId)
    {
        return members.stream()
                .anyMatch(member -> member.getUserId() == userId);

    }

}
