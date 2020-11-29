/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lukuvinkisto.netio;

import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author anoth
 */
public class NBookIO {
    TietokantaDAO db;

    public NBookIO(TietokantaDAO db) {
        this.db = db;
    }
    
    /** Fetches all books stored in memory
     *
     * @return List of all books
     */
    public List<Media> fetch(){
        List<Media> works=db.listBooks(null);
        Collections.sort(works);
        return works;
    }

    /** Fetches all books from the database witch match given input.
     *
     * @param input Searches all works containing input string. Input string can be author part of, or whole name of the name of the book or author.
     * @return List of all results.
     */
    public List<Media> fetch(String input){
        List<Media> works=db.listBooks(input);
        Collections.sort(works);
        return works;
    }

    public boolean remove(Book book) {
        return db.removeBook(book);
    }
    
    public boolean add(Book book){
        return db.addBook(book.getTitle(), book.getAuthor(), String.valueOf(book.getLength()), "", "");
    }
    
}
