package app.controllers;

import app.entities.User;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.services.splitit.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class SplitItGroupController {
    private AccountService accountService;

    public SplitItGroupController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void addRoutes(Javalin app)
    {
        app.get("/splitit", ctx -> index(ctx));
        app.get("/splitit/createGroup", ctx -> showGroup(ctx));
        app.get("/createGroup/cancel", ctx -> cancel(ctx));

        app.post("/createGroup/setName", ctx -> createGroupName(ctx));
        app.post("/createGroup/addMember", ctx -> addMemberToGroup(ctx));
        app.post("/createGroup/create", ctx -> createGroup(ctx));
        app.post("/createGroup/removeMember", ctx -> deleteMemberFromGroup(ctx));
    }

    private void deleteMemberFromGroup(Context ctx)
    {
        List<User> members = ctx.sessionAttribute("addedMembers");
        String userIdParam = ctx.formParam("userId");

        int userId = 0;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }
        try {
            User user = accountService.getUserById(userId);
            User tmpMember = null;
            for(User member: members)
            {
                if(user.getUserId() == member.getUserId()){
                    tmpMember = member;
                }
            }
            members.remove(tmpMember);
            ctx.attribute("addedMembers",members);
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }
        ctx.redirect("/splitit/createGroup");

    }


    private void createGroup(Context ctx)
    {
        List<User> members = ctx.sessionAttribute("addedMembers");
        User currentUser = ctx.sessionAttribute("currentUser");
        String groupName = ctx.sessionAttribute("groupName");
        Group group = null;

        try {
            group = accountService.createGroup(groupName);
        } catch (DatabaseException | IllegalArgumentException e) {
            ctx.attribute("errorMessage", e.getMessage());
        }

        try{
            accountService.addMemberToGroup(currentUser.getUserId(),group.getGroupId());
            for(User member: members){
                accountService.addMemberToGroup(member.getUserId(),group.getGroupId());
            }
        }catch (DatabaseException e){
            ctx.attribute("errorMessage", e.getMessage());
        }
        ctx.redirect("/splitit/");
    }

    private void addMemberToGroup(Context ctx)
    {
        String userIdParam = ctx.formParam("userId");
        if (userIdParam == null) {
            ctx.attribute("errorMessage", "No user selected");
            ctx.render("/splitit/creategroup");
            return;
        }
        int userId = Integer.parseInt(userIdParam);

        try {
            User user = accountService.getUserById(userId);

            List<User> addedMembers = ctx.sessionAttribute("addedMembers");
            if (addedMembers == null) {
                addedMembers = new ArrayList<>();
            }

            if (!accountService.isUserInGroup(addedMembers, userId)){
                addedMembers.add(user);
            } else {
                ctx.attribute("errorMessage","User is already in group!");
            }

            ctx.sessionAttribute("addedMembers", addedMembers);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", e.getMessage());
        }

        ctx.redirect("/splitit/createGroup");
    }

    private void createGroupName(Context ctx)
    {
        String groupName = ctx.formParam("groupName");
        List<User> groupMembers = new ArrayList<>();
        ctx.sessionAttribute("groupName",groupName);
        ctx.sessionAttribute("addedMembers",groupMembers);

        ctx.redirect("/splitit/createGroup");
    }

    private void showGroup(Context ctx)
    {
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null) {
            ctx.redirect("/");
            return;
        }

        try {
            String groupName = ctx.sessionAttribute("groupName");
            List<User> addedMembers = ctx.sessionAttribute("addedMembers");
            if(addedMembers == null){
                addedMembers = new ArrayList<>();
                addedMembers.add(currentUser);
            }

            List<User> allUsers = accountService.getAllUsers();
            List<User> users = new ArrayList<>();

            for (User user : allUsers) {
                if (user.getUserId() != currentUser.getUserId()) {
                    boolean alreadyAdded = false;
                    for (User addedMember : addedMembers) {
                        if (user.getUserId() == addedMember.getUserId()) {
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if (!alreadyAdded) {
                        users.add(user);
                    }
                }
            }

            ctx.attribute("availableUsers",users);
            ctx.attribute("groupName",groupName);
            ctx.attribute("addedMembers",addedMembers);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }
        ctx.render("/splitit/creategroup");
    }

    private void index(Context ctx)
    {
        User user = ctx.sessionAttribute("currentUser");

        if (user == null) {
            ctx.redirect("/");
            return;
        }
        try {
            List<Group> groups = accountService.getUserGroups(user.getUserId());
            ctx.attribute("groups",groups);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }

        ctx.render("/splitit/index.html");
    }

    public void cancel(Context ctx)
    {
        ctx.sessionAttribute("groupName", null);
        ctx.sessionAttribute("addedMembers", null);
        ctx.redirect("/splitit");
    }
}
