package Main;

import Lukuvinkisto.dao.DemoTietokanta;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static TietokantaDAO db;
    
    private static Map<String, String> siteAddresses;
    private static List<String> withListAddresses;
    
    public static void main(String[] args) {
        if ((args.length>0) && args[0].equals("demotietokanta")) {
            DemoTietokanta.luo(DB_FILENAME);
        }
        buildSiteAddresses();
        setUpSite();
        setUpIO();
        setUpWebpages();
    }
    
    static void setUpWebpages() {
        get("/", (request, response) -> {
            return new ModelAndView(buildModel("index"), LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lopeta", (request, response) -> {
            System.exit(0);
            return null;
        }, new VelocityTemplateEngine());
        
        get("/naytakirjat", (request, response) -> {
            HashMap<String, String> model = buildModel("naytakirjat");
            List<Media> booksFound = bookNIO.fetch();
            
            model.put("books", stringifyList(booksFound));
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/naytaartikkelit", (request, response) -> {
            HashMap<String, String> model = buildModel("naytaartikkelit");
            List<Media> articlesFound = articleNIO.fetch();
            
            model.put("articles", stringifyList(articlesFound));
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/naytavideot", (request, response) -> {
            HashMap<String, String> model = buildModel("naytavideot");
            List<Media> videosFound = videoNIO.fetch();
            
            model.put("videos", stringifyList(videosFound));
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/haetagi", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<String> tags = db.listAllTags();
            Collections.sort(tags);
            model.put("allTags", tags);
            model.put("template", siteAddresses.get("haetagi"));
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/selaakirjoja", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<String> tags = db.listAllTags();
            String books = stringifyList(bookNIO.fetch());
            String articles = stringifyList(articleNIO.fetch());
            String videos = stringifyList(videoNIO.fetch());
            Collections.sort(tags);
            model.put("allTags", tags);
            model.put("books", books);
            model.put("articles", articles);
            model.put("videos", videos);
            model.put("template", siteAddresses.get("selaakirjoja"));
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        

        get("/:page", (request, response) -> {
            String page = request.params(":page");
            return new ModelAndView(buildModel(page), LAYOUT);
        }, new VelocityTemplateEngine());

        

        //Artikkelin komennot
        post("/lisaaArtikkeli", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");

            List<String> tagit = new ArrayList();
            if (!request.queryParams("tagit").equals("")) {
                Collections.addAll(tagit,request.queryParams("tagit").split(","));
            }
            
            Boolean articleAdded = articleNIO.add(title, link, tagit);

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

            Boolean bookRemoved = articleNIO.remove(title);

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

            model.put("articles", stringifyList(articlesFound));

            model.put("template", "templates/haeartikkeli.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());
        
        //Videon komennot

        post("/lisaavideo", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String link = request.queryParams("verkkoosoite");
            List<String> tagit = new ArrayList();
            if (!request.queryParams("tagit").equals("")) {
                Collections.addAll(tagit,request.queryParams("tagit").split(","));
            }

            Boolean videoAdded = videoNIO.add(title, link, tagit);

            if (!videoAdded) {
                model.put("error", "Videota ei saatu lisättyä");
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

            Boolean videoRemoved = videoNIO.remove(title);

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

            model.put("videos", stringifyList(videosFound));
            
            model.put("template", "templates/haevideo.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());
        
        post("/haetagi", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<String> tags = db.listAllTags();
            Collections.sort(tags);
            model.put("allTags", tags);
            model.put("template", siteAddresses.get("haetagi"));
            
            String searchWord = request.queryParams("tagi");

            List<Media> itemsFound = db.SearchByTag(searchWord);

            if (itemsFound.isEmpty()) {
                model.put("error", "Ei tagia vastaavia vinkkejä");
                model.put("template", "templates/haetagi.html");
                return new ModelAndView(model, LAYOUT);
            }

            model.put("tags", stringifyList(itemsFound));
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());
        
        //kirjan komennot
        post("/lisaakirja", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("otsikko");
            String author = request.queryParams("kirjoittaja");
            String pages = request.queryParams("sivumaara");
          
            List<String> tagit = new ArrayList();
            if (!request.queryParams("tagit").equals("")) {
                Collections.addAll(tagit,request.queryParams("tagit").split(","));
            }

            Boolean bookAdded = bookNIO.add(title, author, pages, tagit);

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

            Boolean bookRemoved = bookNIO.remove(title, author);

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

            model.put("books", stringifyList(booksFound));
            model.put("template", "templates/haekirja.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());
        
        post("/selaakirjoja", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<String> tags = db.listAllTags();
            Collections.sort(tags);
            model.put("allTags", tags);
            model.put("template", siteAddresses.get("selaakirjoja"));
            String searchWord = request.queryParams("hakusana");
            String searchTag = request.queryParams("tagi");
            String searchCategory = request.queryParams("kategoria");


            List<Media> booksFound = bookNIO.fetch(searchWord);
            List<Media> articlesFound = articleNIO.fetch(searchWord);
            List<Media> videosFound = videoNIO.fetch(searchWord);
            
            if (!searchTag.equalsIgnoreCase("Kaikki")) {
                booksFound = bookNIO.separateByTag(booksFound, searchTag);
                articlesFound = articleNIO.separateByTag(articlesFound, searchTag);
                videosFound = videoNIO.separateByTag(videosFound, searchTag);
            }
            
            if (searchCategory.equalsIgnoreCase("Kaikki") || searchCategory.equalsIgnoreCase("Kirjat")) {
                if (booksFound.isEmpty()) model.put("books", "Ei hakusanaa vastaavia kirjoja");
                else model.put("books", stringifyList(booksFound));
            }
            if (searchCategory.equalsIgnoreCase("Kaikki") || searchCategory.equalsIgnoreCase("Artikkelit")) {
                if (articlesFound.isEmpty()) model.put("articles", "Ei hakusanaa vastaavia artikkeleita");
                else model.put("articles", stringifyList(articlesFound));
            }
            if (searchCategory.equalsIgnoreCase("Kaikki") || searchCategory.equalsIgnoreCase("Videot")) {
                if (videosFound.isEmpty()) model.put("videos", "Ei hakusanaa vastaavia videoita");
                else model.put("videos", stringifyList(videosFound));
            }

            model.put("template", "templates/selaakirjoja.html");
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());
    }
    
    static void buildSiteAddresses(){
        siteAddresses = new HashMap<>();
        siteAddresses.put("index", "templates/index.html");
        
        siteAddresses.put("lisaakirja", "templates/lisaakirja.html");
        siteAddresses.put("lisaaArtikkeli", "templates/lisaaArtikkeli.html");
        siteAddresses.put("lisaavideo", "templates/lisaavideo.html");
        siteAddresses.put("lisaavinkki", "templates/lisaavinkki.html");
        
        siteAddresses.put("haeartikkeli", "templates/haeartikkeli.html");
        siteAddresses.put("haekirja", "templates/haekirja.html");
        siteAddresses.put("haevideo", "templates/haevideo.html");
        siteAddresses.put("haevinkki", "templates/haevinkki.html");
        siteAddresses.put("haetagi", "templates/haetagi.html");
        
        siteAddresses.put("naytakirjat", "templates/naytakirjat.html");
        siteAddresses.put("naytaartikkelit", "templates/naytaartikkelit.html");
        siteAddresses.put("naytavideot", "templates/naytavideot.html");
        siteAddresses.put("naytavinkit", "templates/naytavinkit.html");
        
        siteAddresses.put("poistakirja", "templates/poistakirja.html");
        siteAddresses.put("poistaArtikkeli", "templates/poistaArtikkeli.html");
        siteAddresses.put("poistavideo", "templates/poistavideo.html");
        siteAddresses.put("poistavinkki", "templates/poistavinkki.html");
        
        siteAddresses.put("selaakirjoja", "templates/selaakirjoja.html");
        
        siteAddresses.put("listaelementti", "templates/listaelementti.html");
        
        withListAddresses = new ArrayList<>();
        
        withListAddresses.add("naytavinkit");
        withListAddresses.add("haevinkki");
        withListAddresses.add("poistavinkki");
        withListAddresses.add("lisaavinkki");
    }
    
    
    static HashMap<String, String> buildModel(String page){
        if(withListAddresses.contains(page)) {
            return buildModelWithList(page);
        }
        
        HashMap<String, String> model = new HashMap<>();
        if(!siteAddresses.containsKey(page)){
            return null;
        }
        model.put("template", siteAddresses.get(page));
        return model;
    }
    static HashMap<String, String> buildModelWithList(String page){
        HashMap<String, String> model = new HashMap<>();
        if(!siteAddresses.containsKey(page)){
            return null;
        }
        model.put("template", siteAddresses.get("listaelementti"));
        
        List<Media> allMedia = listAllMedia();
        model.put("lista", stringifyList(allMedia));
        model.put("template2", siteAddresses.get(page));
        
        return model;
    }
    
    static String stringifyList(List<Media> mediaList) {
        String stringified = "";

            for (Media media : mediaList) {
                stringified += media.getAsListElement();
                stringified += "<br>";
            }
            
        return stringified;
    }
    
    static List<Media> listAllMedia() {
        List<Media> list = new ArrayList<>();
        list.addAll(bookNIO.fetch());
        list.addAll(articleNIO.fetch());
        list.addAll(videoNIO.fetch());
        
        Collections.sort(list);
        
        return list;
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
        db = new TietokantaDAO(DB_FILENAME);
        bookNIO = new NBookIO(db);
        articleNIO = new NArticleIO(db);
        videoNIO = new NVideoIO(db);
    }
}
