package Lukuvinkisto;

import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {
    
    @Test
    public void ReturnCorrectTitle() {
        Article article = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("ab", "cd"));
        assertEquals(article.getTitle(), "Wikipedia: The Hitchhiker's Guide to the Galaxy");
    }

    @Test
    public void ReturnCorrectLink() {
        Article article = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("ab", "cd"));
        assertEquals(article.getLink(), "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy");
    }

    @Test
    public void ReturnCorrectTag() {
        Article article = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("ab", "cd"));
        assertEquals(article.getTags().get(0), "ab");
    }

    @Test
    public void SortsCorrectly() {
        ArrayList articles = new ArrayList<Book>();
        articles.add(new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("ab", "cd")));
        articles.add(new Article("Linnunradan käsikirja liftareille", "https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille", List.of("ef", "gh")));
        Collections.sort(articles);
        assertEquals(articles.get(0).toString(), "Linnunradan käsikirja liftareille : https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille, Tagit: ef, gh");
    }
    
    @Test
    public void ReturnCorrectListElement() {
        Article article = new Article(1,"Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("ab", "cd"));
        assertEquals(article.getAsListElement(), "<a href=\"https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy\">Wikipedia: The Hitchhiker's Guide to the Galaxy<a>, Tagit: ab, cd; Luettu <form method=\"GET\" action=\"/artikkeli/1\">\n" +
                "        <input type=\"submit\" name=\"muokkaaartikkeli\" value=\"Muokkaa\" />\n" +
                  "</form>");
    }
}