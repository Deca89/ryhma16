/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *Tiedoston hallintaa: t�m� muoto, koska testaus/tietokanta/mahdollisesti useammat tietokannat?
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
                db.createStatement().execute("CREATE TABLE IF NOT EXISTS Books(id INTEGER PRIMARY KEY, title TEXT UNIQUE NOT NULL, author TEXT, paged INTEGER, genres TEXT, description TEXT)");
                return 2;
            } catch (SQLException ex) {
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
