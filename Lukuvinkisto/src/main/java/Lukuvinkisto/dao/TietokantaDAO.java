package Lukuvinkisto.dao;

import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import Lukuvinkisto.media.Video;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tietokannan hallintaa
 *
 * @author Juuri
 */
public class TietokantaDAO {
    
    String kirjasto;

    /**
     * Luo hallintapohjan
     *
     * @param kirjasto tietokanta
     */
    public TietokantaDAO(String kirjasto) {
        this.kirjasto = kirjasto;
    }

    /**
     * luo yhteyden tietokantaan
     *
     * @return yhteys
     */
    public Connection createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection givenConnection = DriverManager.getConnection("jdbc:sqlite:" + kirjasto + ".db");
            return givenConnection;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TietokantaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * lisää kirja tietokantaan
     *
     * @param title kirjan nimi
     * @param author kirjoittaja
     * @param pages sivujen määrä
     * @param genres listattavat genret
     * @param description kuvaus kirjasta
     * @return true: lisäys onnistui false: lisäys epäonnistui
     */
    public boolean addBook(String title, String author, String pages, List<String> tags) {
        try {
            Connection dM = createConnection();
            PreparedStatement p = dM.prepareStatement("INSERT INTO Books(title, author, pages) VALUES (?, ?, ?)");
            p.setString(1, title);
            p.setString(2, author);
            p.setString(3, pages);
            p.executeUpdate();

            p = dM.prepareStatement("SELECT book_id FROM Books WHERE title=? AND author=?");
            p.setString(1, title);
            p.setString(2, author);
            int id = p.executeQuery().getInt("book_id");
            dM.close();
            return addTags(tags, 1, id);
        } catch (SQLException ex) {
            return false;
        }
    }
   
    public List<Media> listBooks(String searchTerm) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1;
            if (searchTerm==null) {
                p1 = dM.prepareStatement("SELECT * FROM Books");
            } else {
                p1 = dM.prepareStatement("SELECT * FROM Books WHERE lower(title) LIKE ? OR lower(author) LIKE ?");
                String term = "%"+searchTerm.toLowerCase()+"%";
                p1.setString(1, term);
                p1.setString(2, term);
            }
            ResultSet r = p1.executeQuery();
            List<Media> books = new ArrayList<>();
            while (r.next()) {
                List<String> tags = listTags(1, r.getInt("book_id"));
                books.add(new Book(r.getString("title"), r.getString("author"), r.getInt("pages"), tags));
            }
            dM.close();
            return books;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }    
    
    /**
     * poista kirja tietokannasta
     *
     * @param title kirjan nimi
     * @return true: kirjan poisto onnistui false: kirjan poisto epäonnisti
     */
    public boolean removeBook(Book book) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1 = dM.prepareStatement("DELETE FROM Books WHERE title=? AND author=?");
            p1.setString(1, book.getTitle());
            p1.setString(2, book.getAuthor());
            int r = p1.executeUpdate();
            dM.close();
            if (r==1) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Palauttaa Kirjojen lukumäärän tietokannassa
     *
     * @return kirjojen lukumäärä tietokannassa (numerona), -1 = jotain meni vikaan
     */
    public int numberOfBooks() {
        try {
            Connection dM = createConnection();
            PreparedStatement p1 = dM.prepareStatement("SELECT Count(*) AS C FROM Books");
            ResultSet r = p1.executeQuery();

            return r.getInt("C");
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    // Metodit videoille
    //
    
    public boolean addVideo(String title, String link, List<String> tags) {
        try {
            Connection dM = createConnection();
            PreparedStatement p = dM.prepareStatement("INSERT INTO Videos(title, link) VALUES (?, ?)");
            p.setString(1, title);
            p.setString(2, link);
            p.executeUpdate();
            
            p = dM.prepareStatement("SELECT video_id FROM Videos WHERE title=? AND link=?");
            p.setString(1, title);
            p.setString(2, link);
            int id = p.executeQuery().getInt("video_id");
            dM.close();
            return addTags(tags, 2, id);
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Media> listVideos(String searchTerm) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1;
            if (searchTerm==null) {
                p1 = dM.prepareStatement("SELECT * FROM Videos");
            } else {
                p1 = dM.prepareStatement("SELECT * FROM Videos WHERE lower(title) LIKE ?");
                String term = "%"+searchTerm.toLowerCase()+"%";
                p1.setString(1, term);
            }
            ResultSet r = p1.executeQuery();
            List<Media> videos = new ArrayList<>();
            while (r.next()) {
                List<String> tags = listTags(2, r.getInt("video_id"));
                videos.add(new Video(r.getString("title"), r.getString("link"),tags));
            }
            dM.close();
            return videos;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }    
    
    public boolean removeVideo(Video video) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1 = dM.prepareStatement("DELETE FROM Videos WHERE title=?");
            p1.setString(1, video.getTitle());
            int r = p1.executeUpdate();
            dM.close();
            if (r==1) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    // Metodit artikkeleille
    //
    
    public boolean addArticle(String title, String link, List<String> tags) {
        try {
            Connection dM = createConnection();
            PreparedStatement p = dM.prepareStatement("INSERT INTO Articles(title, link) VALUES (?, ?)");
            p.setString(1, title);
            p.setString(2, link);
            p.executeUpdate();
            
            p = dM.prepareStatement("SELECT article_id FROM Articles WHERE title=? AND link=?");
            p.setString(1, title);
            p.setString(2, link);
            int id = p.executeQuery().getInt("article_id");
            dM.close();
            return addTags(tags, 3, id);
        } catch (SQLException ex) {
            return false;
        }
    }

    public List<Media> listArticles(String searchTerm) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1;
            if (searchTerm==null) {
                p1 = dM.prepareStatement("SELECT * FROM Articles");
            } else {
                p1 = dM.prepareStatement("SELECT * FROM Articles WHERE lower(title) LIKE ?");
                String term = "%"+searchTerm.toLowerCase()+"%";
                p1.setString(1, term);
            }
            ResultSet r = p1.executeQuery();
            List<Media> articles = new ArrayList<>();
            while (r.next()) {
                List<String> tags = listTags(3, r.getInt("article_id"));
                articles.add(new Article(r.getString("title"), r.getString("link"), tags));
            }
            dM.close();
            return articles;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }    
    
    public boolean removeArticle(Article article) {
        try {
            Connection dM = createConnection();
            PreparedStatement p1 = dM.prepareStatement("DELETE FROM Articles WHERE title=?");
            p1.setString(1, article.getTitle());
            int r = p1.executeUpdate();
            dM.close();
            if (r==1) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    // Metodit tageille
    //
    
    private boolean addTags(List<String> tags, int item_type, int item_id) {
        if (tags==null) {
            return true;
        }
        try {
            Connection dM = createConnection();
            for (String tag : tags) {
                PreparedStatement p = dM.prepareStatement("INSERT INTO Tags(item_type, item_id, tag) VALUES (?, ?, ?)");
                p.setInt(1, item_type);
                p.setInt(2, item_id);
                p.setString(3, tag);
                p.executeUpdate();
            }
            dM.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    private List<String> listTags(int item_type, int item_id) {
        try {
            Connection dM = createConnection();
            PreparedStatement p = dM.prepareStatement("SELECT tag FROM Tags WHERE item_type=? AND item_id=?");
            p.setInt(1, item_type);
            p.setInt(2, item_id);
            ResultSet r = p.executeQuery();
            List<String> tags = new ArrayList<>();
            while (r.next()) {
                tags.add(r.getString("tag"));
            }
            dM.close();
            return tags;
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
