package Main;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import Lukuvinkisto.netio.NBookIO;
import java.awt.Desktop;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {

    private static final String DB_FILENAME = "Lukuvinkisto";
    static String LAYOUT = "templates/layout.html";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //run();
        int foundPort = findOutPort();
        port(foundPort);
        try {
            //specify the protocol along with the URL
            Desktop.getDesktop().browse(new URL("http://localhost:" + foundPort + "/").toURI());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TiedostoDAO dbFile = new TiedostoDAO();
        dbFile.createFile(DB_FILENAME);
        TietokantaDAO db = new TietokantaDAO(DB_FILENAME);
        NBookIO bookNIO = new NBookIO(db);

        get("/", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lisaavinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/lisaavinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lisaakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/lisaakirja.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/poistavinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistavinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/poistakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistakirja.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haevinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/haevinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haekirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/haekirja.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lopeta", (request, response) -> {
            System.exit(0);
            return null;
        }, new VelocityTemplateEngine());

        post("/lisaakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String author = request.queryParams("kirjoittaja");
            int pages = Integer.valueOf(request.queryParams("sivumaara"));

            Boolean bookAdded = bookNIO.add(new Book(title, author, pages));

            if (!bookAdded) {
                model.put("error", "Kirjaa ei saatu lisättyä");
                model.put("template", "templates/lisaakirja.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Kirja lisätty lukuvinkistöön");
            model.put("template", "templates/lisaakirja.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/poistakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String author = request.queryParams("kirjoittaja");

            Boolean bookRemoved = bookNIO.remove(new Book(title, author, 0));

            if (!bookRemoved) {
                model.put("error", "Kirjaa ei saatu poistettua");
                model.put("template", "templates/poistakirja.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Kirja poistettu lukuvinkistöstä");
            model.put("template", "templates/poistakirja.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/haekirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String searchWord = request.queryParams("hakusana");

            List<Media> booksFound = bookNIO.fetch(searchWord);

            if (booksFound.isEmpty()) {
                model.put("error", "Ei hakusanaa vastaavia kirjoja");
                model.put("template", "templates/haekirja.html");
                return new ModelAndView(model, LAYOUT);
            }

            String books = "";

            for (Media book : booksFound) {
                books += book;
                books += "<br>";
            }

            model.put("books", books);
            model.put("template", "templates/haekirja.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

    }

    static int findOutPort() {
        if (portFromEnv != null) {
            return Integer.parseInt(portFromEnv);
        }

        return 4567;
    }

    static String portFromEnv = new ProcessBuilder().environment().get("PORT");

    static void setEnvPort(String port) {
        portFromEnv = port;
    }

}
