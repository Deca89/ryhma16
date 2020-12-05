package Lukuvinkisto.dao;

import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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
        instance.addBook("Taikavuori", "Thomas Mann", "800", List.of("ab", "cd"));
        instance.addBook("Ulysses", "James Joyce", "111", List.of("ef", "gh"));
        instance.addBook("Malleus Maleficarum", "The Spanish Inquisition", "612", List.of("ij", "kl"));
        instance.addBook("Harry Potter and The Prisoner Of Azkaban", "J. K. Rowling", "412", List.of("mn", "op"));
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
        boolean result = instance.addBook("1984", "George Orwell", "209", null);

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
        Book book = new Book("Ulysses", "James Joyce", 409, null);
        boolean result2 = instance.removeBook(book.getTitle(), book.getAuthor());
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
        Book book = new Book("Ulysses and Donald Duck", "James Joyce", 409, null);
        boolean result2 = instance.removeBook(book.getTitle(), book.getAuthor());
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
    
    @Test
    public void testNumberOfTags() {
        int expResult = 2;
        int result = instance.listBooks("vuori").get(0).getTags().size();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTag1() {
        String expResult = "ab";
        String result = instance.listBooks("vuori").get(0).getTags().get(0);
        assertEquals(expResult, result);
    }

    @Test
    public void testTag2() {
        String expResult = "cd";
        String result = instance.listBooks("vuori").get(0).getTags().get(1);
        assertEquals(expResult, result);
    }
}
