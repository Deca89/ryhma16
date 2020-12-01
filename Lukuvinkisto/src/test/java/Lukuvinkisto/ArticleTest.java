package Lukuvinkisto;

import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Video;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {
    
    @Test
    public void ReturnCorrectTitle() {
        Article article = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy");
        assertEquals(article.getTitle(), "Wikipedia: The Hitchhiker's Guide to the Galaxy");
    }

    @Test
    public void ReturnCorrectLink() {
        Article article = new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy");
        assertEquals(article.getLink(), "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy");
    }

    @Test
    public void SortsCorrectly() {
        ArrayList articles = new ArrayList<Book>();
        articles.add(new Article("Wikipedia: The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy"));
        articles.add(new Article("Linnunradan käsikirja liftareille", "https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille"));
        Collections.sort(articles);
        assertEquals(articles.get(0).toString(), "Linnunradan käsikirja liftareille : https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille");
    }

}