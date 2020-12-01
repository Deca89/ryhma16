package Lukuvinkisto;

import Lukuvinkisto.media.Book;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    
    @Test
    public void ReturnCorrectAuthor() {
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203);
        assertEquals(book.getAuthor(), "Adams, Douglas");
    }
    
    @Test
    public void ReturnCorrectTitle() {
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203);
        assertEquals(book.getTitle(), "Linnunradan käsikirja liftareille");
    }

    @Test
    public void ReturnCorrectPages() {
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203);
        assertEquals(book.getLength(), 203);
    }

    @Test
    public void SortsCorrectly() {
        ArrayList books = new ArrayList<Book>();
        books.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203));
        books.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222));
        Collections.sort(books);
        assertEquals(books.get(0).toString(), "Adams, Douglas : Linnunradan käsikirja liftareille. sivumäärä: 203");
    }
    @Test
    public void ReturnCorrectListElement() {
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203);
        assertEquals(book.getAsListElement(), book.toString());
    }
}
