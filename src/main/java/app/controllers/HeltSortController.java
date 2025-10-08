package app.controllers;

import app.entities.heltsort.Beton;
import app.entities.heltsort.Gips;
import app.entities.heltsort.Premalet;
import app.exceptions.DatabaseException;
import app.persistence.heltsort.BetonMapper;
import app.persistence.ConnectionPool;
import app.persistence.heltsort.GipsMapper;
import app.persistence.heltsort.PremaletMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class HeltSortController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("getresult", ctx -> getResult(ctx, connectionPool));
    }

    private static void getResult(Context ctx, ConnectionPool connectionPool) {
        String material = ctx.formParam("materiale");
        String userMeasurement = ctx.formParam("mode");

        try {
            double squareMeter = calculateSquareMeter(ctx, userMeasurement);
            String sqMeter = doubleFormatter(squareMeter);
            MaterialResult materialResult;

            switch (material) {
                case "Gips" -> materialResult = getDrywallResult();
                case "Gasbeton/Beton" -> materialResult = getConcreteResult();
                case "Eksisterende væg" -> materialResult = getPrepaintedResult();
                default -> {
                    ctx.sessionAttribute("inputerror", "Ukendt materiale valgt");
                    ctx.redirect("/");
                    return;
                }
            }

            finalResult(ctx, squareMeter, material, sqMeter, materialResult);

        } catch (NumberFormatException e) {
            ctx.sessionAttribute("inputerror", "Indtast venligst mål");
            ctx.redirect("/");
        } catch (DatabaseException e) {
            ctx.sessionAttribute("inputerror", "Hov noget gik galt, prøv igen");
            ctx.redirect("/");
        }

    }

    private static MaterialResult getDrywallResult() throws DatabaseException {
        Gips gips = GipsMapper.getGipsPrice();
        return new MaterialResult(gips.getWithoutFiltPrice(), gips.getWithFiltPrice(), gips.workDescription(false), gips.workDescription(true), true);
    }

    private static MaterialResult getConcreteResult() throws DatabaseException {
        Beton beton = BetonMapper.getBetonPrice();
        return new MaterialResult(beton.getWithoutFiltPrice(), beton.getWithFiltPrice(), beton.workDescription(false), beton.workDescription(true), true);
    }

    private static MaterialResult getPrepaintedResult() throws DatabaseException {
        Premalet premalet = PremaletMapper.getPremaletPrice();
        return new MaterialResult(premalet.getOneCoatPrice(), premalet.getTwoCoatsPrice(), premalet.workDescription(false), premalet.workDescription(true), false);
    }

    private static void finalResult(Context ctx, double squareMeter, String material, String sqMeter, MaterialResult materialResult) {
        double finalResultOne = (squareMeter * materialResult.resultOne);
        double finalResultTwo = (squareMeter * materialResult.resultTwo);

        if (materialResult.filt) {
            ctx.attribute("result1title", "Tilbud uden filt:");
            ctx.attribute("result2title", "Tilbud med filt:");
        } else {
            ctx.attribute("result1title", "Tilbud på ét lag maling");
            ctx.attribute("result2title", "Tilbud på to lag maling");
        }

        ctx.attribute("materialevalg", material);
        ctx.attribute("maalvalg", sqMeter);
        ctx.attribute("result1", "DKK " + doubleFormatter(finalResultOne));
        ctx.attribute("result2", "DKK " + doubleFormatter(finalResultTwo));
        ctx.attribute("resultOneDescription", materialResult.descriptionOne);
        ctx.attribute("resultTwoDescription", materialResult.descriptionTwo);

        ctx.render("resultpage.html");
    }

    private static String doubleFormatter(double price) {
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("da", "DK")));
        return df.format(price);
    }

    private static double calculateSquareMeter(Context ctx, String userMeasurement) {
        if ("m2".equals(userMeasurement)) {
            return Double.parseDouble(ctx.formParam("m2"));
        } else {
            double height = Double.parseDouble(ctx.formParam("height"));
            double width = Double.parseDouble(ctx.formParam("width"));
            return height * width;
        }
    }

    private static class MaterialResult {
        double resultOne;
        double resultTwo;
        String descriptionOne;
        String descriptionTwo;
        boolean filt;

        MaterialResult(double resultOne, double resultTwo, String descriptionOne, String descriptionTwo, boolean filt) {
            this.resultOne = resultOne;
            this.resultTwo = resultTwo;
            this.descriptionOne = descriptionOne;
            this.descriptionTwo = descriptionTwo;
            this.filt = filt;
        }
    }
}