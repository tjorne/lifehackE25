package app.controllers;

import app.entities.User;
import app.entities.splitit.Group;
import app.exceptions.DatabaseException;
import app.services.splitit.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SplitItController {
    private AccountService accountService;

    public SplitItController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void addRoutes(Javalin app)
    {
        app.get("/splitit", ctx -> index(ctx));
        app.get("/splitit/createGroup", ctx -> createGroup(ctx));
    }

    private void createGroup(Context ctx)
    {
        try {
            List<User> users = accountService.getAllUsers();
            ctx.attribute("users",users);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }
        ctx.render("/splitit/creategroup");
    }

    private void index(Context ctx)
    {
        try {
            List<Group> groups = accountService.getAllGroups();
            List<User> users = accountService.getAllUsers();

            ctx.attribute("groups",groups);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage",e.getMessage());
        }

        ctx.render("/splitit/index.html");
    }
}
