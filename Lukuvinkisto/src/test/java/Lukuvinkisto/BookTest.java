package Lukuvinkisto;

import Lukuvinkisto.media.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    
    @Test
    public void ReturnCorrectAuthor() {
        List<String> tags = List.of("ab", "cd");
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, tags);
        assertEquals(book.getAuthor(), "Adams, Douglas");
    }
    
    @Test
    public void ReturnCorrectTitle() {
        List<String> tags = List.of("ab", "cd");
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, tags);
        assertEquals(book.getTitle(), "Linnunradan käsikirja liftareille");
    }

    @Test
    public void ReturnCorrectPages() {
        List<String> tags = List.of("ab", "cd");
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, tags);
        assertEquals(book.getLength(), 203);
    }

    @Test
    public void ReturnCorrectTag() {
        List<String> tags = List.of("ab", "cd");
        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, tags);
        assertEquals(book.getTags().get(0), "ab");
    }

    @Test
    public void SortsCorrectly() {
        List<String> tags1 = List.of("ab", "cd");
        List<String> tags2 = List.of("ef", "gh");
        ArrayList books = new ArrayList<Book>();
        books.add(new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, tags1));
        books.add(new Book("Maailmanlopun ravintola", "Adams, Douglas", 222, tags2));
        Collections.sort(books);
        assertEquals(books.get(0).toString(), "Adams, Douglas : Linnunradan käsikirja liftareille, sivumäärä: 203, Tagit: ab, cd");
    }
    @Test
    public void ReturnCorrectListElement() {
        Book book = new Book(1,"Linnunradan käsikirja liftareille", "Adams, Douglas", 203, List.of("ab", "cd"));
        assertEquals(book.getAsListElement(), book.toString() + " <form method=\"GET\" action=\"/kirja/1\">\n" +
                "        <input type=\"submit\" name=\"muokkaakirjaa\" value=\"Muokkaa\" />\n" +
                  "</form>");
    }
}
