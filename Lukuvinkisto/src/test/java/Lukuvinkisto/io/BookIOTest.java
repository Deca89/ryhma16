package Lukuvinkisto.io;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Media;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lukuvinkisto.io.BookIO;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BookIOTest {
    BookIO bookIO;
    Queue<String> inputs;
    InputInterface io;

    @Before
    public void setUp() {
        TiedostoDAO dbFile = new TiedostoDAO();
        dbFile.createFile("testi");
        TietokantaDAO db = new TietokantaDAO("testi");
        bookIO = new BookIO(db);
        inputs = new LinkedList<String>();
        io = new InputStub(inputs);
    }

    @After
    public void tearDownClass() {
        File file = new File("testi.db"); 
        file.delete(); 
    }
    
    @Test
    public void testNewBookTipAuthor() {
        inputs.add("Linnunradan käsikirja liftareille");
        inputs.add("Adams, Douglas");
        inputs.add("203");
        Book book = bookIO.NewTip(io);
        assertEquals(book.getAuthor(), "Adams, Douglas");
    }   

    @Test
    public void testNewBookTipTitle() {
        inputs.add("Linnunradan käsikirja liftareille");
        inputs.add("Adams, Douglas");
        inputs.add("203");
        Book book = bookIO.NewTip(io);
        assertEquals(book.getTitle(), "Linnunradan käsikirja liftareille");
    } 
    
    @Test
    public void testNewBookTiplenght() {
        inputs.add("Linnunradan käsikirja liftareille");
        inputs.add("Adams, Douglas");
        inputs.add("203");
        Book book = bookIO.NewTip(io);
        assertEquals(book.getLength(), 203);
    } 
    
    @Test
    public void testBookFetchAllFirstOk() {
        bookIO.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222));
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));
        List<Media> list = bookIO.fetch();
        assertEquals(list.get(0).getTitle(), "Linnunradan käsikirja liftareille");
    }

    @Test
    public void testBookFetchAllSecondOk() {
        bookIO.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222));
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));
        List<Media> list = bookIO.fetch();
        assertEquals(list.get(1).getTitle(), "Maailmanlopun ravintola");
    }
    
    @Test
    public void testBookSearchOk() {
        bookIO.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222));
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));
        List<Media> list = bookIO.fetch("MAAILMANLOPUN");
        assertEquals(list.get(0).getTitle(), "Maailmanlopun ravintola");
    }

    @Test
    public void testBookDisappearedAfterRemove() {
        bookIO.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222));
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));

        inputs.add("Maailmanlopun ravintola");
        inputs.add("Adams, Douglas");
        bookIO.remove(io);
        List<Media> list = bookIO.fetch("MAAILMANLOPUN");
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testBookRemoveReturnFalseIfnotExisting() {
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));

        inputs.add("Maailmanlopun ravintola");
        inputs.add("Adams, Douglas");
        boolean r = bookIO.remove(io);
        assertFalse(r);
    }
    
    @Test
    public void testBookRemoveReturnTrueIfExisting() {
        bookIO.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));

        inputs.add("Linnunradan käsikirja liftareille");
        inputs.add("Adams, Douglas");
        boolean r = bookIO.remove(io);
        assertTrue(r);
    }
}
