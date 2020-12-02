package Lukuvinkisto.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *Tiedoston hallintaa: tämä muoto, koska testaus/tietokanta/mahdollisesti useammat tietokannat?
 * @author Juuri
 */
public class TiedostoDAO {
    
    /**
     * Luo tiedoston, johon tallennetaan data
     *
     * @param fileName tiedoston nimi
     * @return 1: tiedosto jo olemassa 2: tiedoston luonti onnistui 3: tapahtui
     * odottamaton ongelma
     */
    public Integer createFile(String fileName) {
        boolean fileExists = fileExists(fileName);

        if (fileExists) {
            return 1;
        } else {
            try {
                Connection db = DriverManager.getConnection("jdbc:sqlite:" + fileName + ".db");
                db.createStatement().execute("CREATE TABLE Books (book_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL UNIQUE, author TEXT, pages TEXT);");
                db.createStatement().execute("CREATE TABLE Videos (video_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL UNIQUE, link TEXT);");
                db.createStatement().execute("CREATE TABLE Articles (article_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL UNIQUE, link TEXT);");
                db.createStatement().execute("CREATE TABLE Tags (tag_id INTEGER PRIMARY KEY AUTOINCREMENT, item_type INTEGER, item_id INTEGER, tag TEXT);");
                // item_type: book=1, video=2, article=3
                return 2;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 3;
                
            }
        }

    }

    /**
     * Tarkistaa onko tiedosto jo olemassa
     *
     * @param fileName tiedoston nimi
     * @return true: on olemassa false: ei ole olemassa
     */
    public boolean fileExists(String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
            String check = fileName + ".db";
            File file = new File(check);
            return file.exists();
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    /**
     * Poistaa kyseisen tiedoston
     *
     * @param fileName poistettavan tiedoston nimi
     * @return true: tiedosto poistettu false: tiedostoa ei poistettu
     */
    public boolean deleteFile(String fileName) {
        if (fileExists(fileName)) {
            String check = fileName + ".db";
            File file = new File(check);
            file.delete();
            return true;
        }
        return false;
    }

}
