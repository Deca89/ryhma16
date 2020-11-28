package Main;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import Lukuvinkisto.media.Video;
import Lukuvinkisto.netio.NArticleIO;
import Lukuvinkisto.netio.NBookIO;
import Lukuvinkisto.netio.NVideoIO;
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
    
    private static NBookIO bookNIO;
    private static NArticleIO articleNIO;
    private static NVideoIO videoNIO;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        setUpSite();
        setUpIO();
        setUpWebpages();
    }
    
    static void setUpWebpages() {
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

        get("/poistavinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistavinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haevinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/haevinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lopeta", (request, response) -> {
            System.exit(0);
            return null;
        }, new VelocityTemplateEngine());

        //Artikkelin komennot
        get("/lisaaArtikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/lisaaArtikkeli.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/poistaArtikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistaArtikkeli.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haeartikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/haeartikkeli.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/lisaaArtikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");

            Boolean articleAdded = articleNIO.add(new Article(title, link));

            if (!articleAdded) {
                model.put("error", "Artikkelia ei saatu lisättyä");
                model.put("template", "templates/lisaaArtikkeli.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Artikkeli lisätty lukuvinkistöön");
            model.put("template", "templates/lisaaArtikkeli.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/poistaArtikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");

            Boolean bookRemoved = articleNIO.remove(new Article(title, link));

            if (!bookRemoved) {
                model.put("error", "Artikkelia ei saatu poistettua");
                model.put("template", "templates/poistaArtikkeli.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Artikkeli poistettu lukuvinkistöstä");
            model.put("template", "templates/poistaArtikkeli.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/haeartikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String searchWord = request.queryParams("hakusana");

            List<Media> articlesFound = articleNIO.fetch(searchWord);

            if (articlesFound.isEmpty()) {
                model.put("error", "Ei hakusanaa vastaavia artikkeleita");
                model.put("template", "templates/haeartikkeli.html");
                return new ModelAndView(model, LAYOUT);
            }

            String articles = "";

            for (Media article : articlesFound) {
                articles += "<a href=\"" + article.getLink() + "\">" + article.getTitle() + "<a>";
                articles += "<br>";
            }

            model.put("articles", articles);
            model.put("template", "templates/haeartikkeli.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        //Videon komennot
        get("/lisaavideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/lisaavideo.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/poistavideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistavideo.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haevideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/haevideo.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/lisaavideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");

            Boolean videoAdded = videoNIO.add(new Video(title, link));

            if (!videoAdded) {
                model.put("error", "Videoa ei saatu lisättyä");
                model.put("template", "templates/lisaavideo.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Video lisätty lukuvinkistöön");
            model.put("template", "templates/lisaavideo.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/poistavideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");

            Boolean videoRemoved = videoNIO.remove(new Video(title, link));

            if (!videoRemoved) {
                model.put("error", "Videota ei saatu poistettua");
                model.put("template", "templates/poistavideo.html");
                return new ModelAndView(model, LAYOUT);
            }
            model.put("lisatty", "Video poistettu lukuvinkistöstä");
            model.put("template", "templates/poistavideo.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/haevideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String searchWord = request.queryParams("hakusana");

            List<Media> videosFound = videoNIO.fetch(searchWord);

            if (videosFound.isEmpty()) {
                model.put("error", "Ei hakusanaa vastaavia videoita");
                model.put("template", "templates/haevideo.html");
                return new ModelAndView(model, LAYOUT);
            }

            String videos = "";

            for (Media video : videosFound) {
                videos += "<a href=\"" + video.getLink() + "\">" + video.getTitle() + "<a>";
                videos += "<br>";
            }

            model.put("videos", videos);
            model.put("template", "templates/haevideo.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        //kirjan komennot
        get("/lisaakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/lisaakirja.html");
            return new ModelAndView(model, LAYOUT);
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

        get("/poistakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/poistakirja.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/haekirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
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
    
    static void setUpSite(){
        int foundPort = findOutPort();
        port(foundPort);
        try {
            //specify the protocol along with the URL
            Desktop.getDesktop().browse(new URL("http://localhost:" + foundPort + "/").toURI());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    static void setUpIO(){
        (new TiedostoDAO()).createFile(DB_FILENAME);
        TietokantaDAO db = new TietokantaDAO(DB_FILENAME);
        bookNIO = new NBookIO(db);
        articleNIO = new NArticleIO(db);
        videoNIO = new NVideoIO(db);
    }
}
