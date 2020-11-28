package Lukuvinkisto.dao;

import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TietokantaDAOTest {
    
    TiedostoDAO db = new TiedostoDAO();
    Integer a = db.createFile("TestDatabase");
    TietokantaDAO instance = new TietokantaDAO("TestDatabase");
    
    @After
    public void deleteOutputFile() {
          db.deleteFile("TestDatabase");
    }
    
    @Before
    public void addBooksToTestDatabaseBeforeRunningTests() {
        instance.addBook("Taikavuori", "Thomas Mann", "800", " ", " ");
        instance.addBook("Ulysses", "James Joyce", "111", " ", " ");
        instance.addBook("Malleus Maleficarum", "The Spanish Inquisition", "612", " ", " ");
        instance.addBook("Harry Potter and The Prisoner Of Azkaban", "J. K. Rowling", "412", " ", " ");
    }
    
    /**
     * Test of createConnection method, of class TietokantaDAO.
     */
    @Test
    public void testCreateConnection() throws SQLException {

        Integer expResult = 1;
        Integer result = null;
        Connection testConnection = instance.createConnection();
        
        testConnection.close();
        
        if (!testConnection.equals(null)) {
            result = 1;
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of addBook method, of class TietokantaDAO.
     */
    @Test
    public void testAddBook() {
        boolean result = instance.addBook("1984", "George Orwell", "209", " ", " ");
        boolean expResult = true;        

        assertEquals(true, result);
    }
    

    /**
     * Test of listBooks method, of class TietokantaDAO.
     */
    @Test
    public void testListBooks() {

        String searchTerm = "";        
        List<Media> result = instance.listBooks(searchTerm);
        assertEquals(4, result.size());
        
        searchTerm = "Taikavuori";        
        result = instance.listBooks(searchTerm);
        assertEquals(1, result.size());
        
        searchTerm = null;        
        result = instance.listBooks(searchTerm);
        assertEquals(4, result.size());
    }

    /**
     * Test of removeBook method, of class TietokantaDAO.
     */
    @Test
    public void testRemoveBook() {
        Book book = new Book("Ulysses", "James Joyce", 409);
        boolean result2 = instance.removeBook(book);
        assertEquals(true, result2);
        
        String searchTerm = null;
        List<Media> result = instance.listBooks(searchTerm);
        assertEquals(3, result.size());
    }

    @Test
    public void testRemoveBookThatDoesNotExist() {
        // Checking that the book does not exist yet
        String searchTerm = "Ulysses and Donald Duck";        
        List<Media> result = instance.listBooks(searchTerm);
        assertEquals(0, result.size());
        
        // Removing a non-exisgent book
        Book book = new Book("Ulysses and Donald Duck", "James Joyce", 409);
        boolean result2 = instance.removeBook(book);
        assertEquals(false, result2);
        
        // Checking the list size afterwards
        searchTerm = "";        
        result = instance.listBooks(searchTerm);
        assertEquals(4, result.size());
    }
    

    /**
     * Test of numberOfBooks method, of class TietokantaDAO.
     */
    @Test
    public void testNumberOfBooks() {
        int expResult = 4;
        int result = instance.numberOfBooks();
        assertEquals(expResult, result);
    }
    
}
