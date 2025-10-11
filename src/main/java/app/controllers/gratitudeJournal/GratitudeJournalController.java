package app.controllers.gratitudeJournal;

import app.entities.gratitudeJournal.ItemType;
import app.entities.gratitudeJournal.JournalItem;
import app.persistence.gratitudeJournal.JournalMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GratitudeJournalController {

    public static void addRoutes(Javalin app) {
        // Vis journal-siden (formularen)
        app.get("/journal", GratitudeJournalController::renderForm);

        // Modtag og gem indsendt formular
        app.post("/journal", GratitudeJournalController::submitForm);
    }

    private static void renderForm(Context ctx) {
        // Tilpas stien til din skabelonplacering
        ctx.render("gratitudeJournal/index.html");
    }

    private static void submitForm(Context ctx) {
        try {
            // 1) Læs form-værdier
            // Dato (skal matche <input name="logDate">)
            String logDateStr = ctx.formParam("logDate");
            LocalDate logDate = (logDateStr != null && !logDateStr.isBlank())
                    ? LocalDate.parse(logDateStr)
                    : LocalDate.now();

            // Gentagne felter bruger samme name => fås som liste
            List<String> grateful = ctx.formParams("grateful");  // 0..n
            List<String> smile = ctx.formParams("smile");     // 0..n

            // Enkeltfelter
            String inspired = trimOrNull(ctx.formParam("inspired"));
            String people = trimOrNull(ctx.formParam("people"));
            String affirmation = trimOrNull(ctx.formParam("affirmation"));

            // 2) Map til JournalItem-objekter
            List<JournalItem> items = new ArrayList<>();
            addList(items, grateful, ItemType.GRATEFUL);
            addList(items, smile, ItemType.SMILE);
            addSingle(items, inspired, ItemType.INSPIRED);
            addSingle(items, people, ItemType.PEOPLE);

            // Simpel servervalidering
            if (items.isEmpty()) {
                ctx.attribute("message", "Skriv mindst én ting, du er taknemmelig for 😊");
                ctx.status(400);
                ctx.render("gratitudeJournal/index.html");
                return;
            }

            // 3) Gem i DB (én transaktion: log + items)
            long logId = JournalMapper.createLogWithItems(logDate, items);

            // 4) Vis bekræftelse
            ctx.attribute("message", "Tak! Din journal er gemt (id: " + logId + ").");
            ctx.render("gratitudeJournal/index.html");

        } catch (Exception e) {
            ctx.attribute("message", "Uventet fejl: " + e.getMessage());
            ctx.status(500);
            ctx.render("gratitudeJournal/index.html");
        }
    }

    private static void addList(List<JournalItem> items, List<String> values, ItemType type) {
        if (values == null) return;
        int pos = 1;
        for (String v : values) {
            String s = trimOrNull(v);
            if (s != null) {
                JournalItem it = new JournalItem();
                it.setItemType(type);
                it.setPosition(pos++);
                it.setContent(s);
                items.add(it);
            }
        }
    }

    private static void addSingle(List<JournalItem> items, String value, ItemType type) {
        if (value == null) return;
        JournalItem it = new JournalItem();
        it.setItemType(type);
        it.setPosition(1);
        it.setContent(value);
        items.add(it);
    }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}