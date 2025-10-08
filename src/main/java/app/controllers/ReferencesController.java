package app.controllers;

import app.entities.references.DTO.BookWithTheorist;
import app.entities.references.Fields;
import app.entities.references.Theorists;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.references.BooksMapper;
import app.persistence.references.FieldsMapper;
import app.persistence.references.TheoristsMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class ReferencesController {
    public static void addRoutes(Javalin app) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        //app.get("/references", ctx -> index(ctx));
        app.get("/references", ctx -> fieldsDropdown(ctx, connectionPool));

        app.post("/references", ctx -> saveSelectedField(ctx));

        app.get("/theorists", ctx -> showTheorists(ctx, connectionPool));

        app.post("/theorists", ctx -> saveSelectedTheorist(ctx));

        app.get("/book", ctx -> showTheoristBook(ctx, connectionPool));

    }

    private static void index(Context ctx) {
        ctx.render("/references/index.html");
    }

    public static void fieldsDropdown(Context ctx, ConnectionPool connectionPool) {
        List<Fields> fieldsList = FieldsMapper.getAllFields(connectionPool);
        ctx.attribute("fields", fieldsList);
        ctx.render("references/index.html");

    }


    public static void saveSelectedField(Context ctx) {
        try {
            int selectedFieldId = Integer.parseInt(ctx.formParam("field"));
            ctx.sessionAttribute("selectedFieldId", selectedFieldId);
            ctx.redirect("/theorists");
        }
        catch (NumberFormatException e){
            ctx.result("numberFormatException in saveSelectedField");

        }
    }

        public static void saveSelectedTheorist(Context ctx) {
        try {
            int selectedTheoristId = Integer.parseInt(ctx.formParam("theorist"));

            ctx.sessionAttribute("selectedTheoristId", selectedTheoristId);

            ctx.redirect("/book");
        }
        catch (NumberFormatException e){
            ctx.result("numberFormatException in saveSelectedTheorist");

        }
    }

    public static void showTheorists(Context ctx, ConnectionPool connectionPool) throws DatabaseException{
        Integer selectedFieldId = ctx.sessionAttribute("selectedFieldId");

        if(selectedFieldId == null){
            ctx.redirect("/references");
            return;
        }
        List<Theorists> theorists = null;
        try {
            theorists = TheoristsMapper.getTheoristsByField(selectedFieldId, connectionPool);
            ctx.attribute("theorists", theorists);
            ctx.render("/references/theorists.html");
        } catch (DatabaseException e) {
            throw new DatabaseException("Theorist not found", e.getMessage());
        }
    }
    public static void showTheoristBook(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Integer selectedTheoristId = ctx.sessionAttribute("selectedTheoristId");
        Integer selectedFieldId = ctx.sessionAttribute("selectedFieldId");

        if(selectedTheoristId == null || selectedFieldId == null){
            ctx.redirect("/references");
            return;
        }

        List<BookWithTheorist> theoristBook = null;
        try{
        theoristBook = BooksMapper.getBookByTheoristAndField(selectedFieldId,selectedTheoristId, connectionPool);
        ctx.attribute("books", theoristBook);
        ctx.render("/references/book.html");
        } catch(DatabaseException e) {
            throw new DatabaseException("Book not found", e.getMessage());
        }
    }
}