package app;

import app.config.ThymeleafConfig;
import app.controllers.*;
import app.persistence.ConnectionPool;
import app.persistence.splitit.GroupMapper;
import app.persistence.splitit.GroupMemberMapper;
import app.services.splitit.AccountService;
import app.services.splitit.AccountServiceImpl;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main 
{
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehack";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));
        UserController.addRoutes(app);
        TimeZonesController.addRoutes(app);


        GroupMapper groupMapper = new GroupMapper(connectionPool);
        GroupMemberMapper groupMemberMapper = new GroupMemberMapper(connectionPool);
        AccountService accountService = new AccountServiceImpl(groupMapper,groupMemberMapper);
        SplitItController splitItController = new SplitItController(accountService);
        splitItController.addRoutes(app);

    }
}