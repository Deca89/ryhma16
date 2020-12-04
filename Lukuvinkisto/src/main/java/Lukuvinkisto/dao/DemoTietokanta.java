package Lukuvinkisto.dao;

import Lukuvinkisto.media.Article;
import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Video;
import java.io.File;
import java.util.List;

public class DemoTietokanta {

    public static void luo(String db_filename) {
        File file = new File(db_filename+".db"); 
        if (file != null) {
            file.delete();
        } 

        (new TiedostoDAO()).createFile(db_filename);
        TietokantaDAO db = new TietokantaDAO(db_filename);

        Article article = new Article("The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("wikipedia", "englanti", "liftausta"));
        db.addArticle(article.getTitle(), article.getLink(), article.getTags());
        article = new Article("Linnunradan käsikirja liftareille", "https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille", List.of("wikipedia", "liftausta"));
        db.addArticle(article.getTitle(), article.getLink(), article.getTags());
        db.addArticle("The Game - 30th Anniversary Edition", "https://www.bbc.co.uk/programmes/articles/1g84m0sXpnNCv84GpN2PLZG/the-game-30th-anniversary-edition", List.of("peli", "liftausta"));
        
        Video video = new Video("Hitchhiker's Guide - Earth Destroyed and Guide Introduction (HD)", "https://youtu.be/Z1Ba4BbH0oY", List.of("elokuva", "liftausta"));
        db.addVideo(video.getTitle(), video.getLink(), video.getTags());
        video = new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU", List.of("äänikirja", "englanti", "osa1","liftausta"));
        db.addVideo(video.getTitle(), video.getLink(), video.getTags());

        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, List.of("osa1", "liftausta"));
        db.addBook(book.getTitle(), book.getAuthor(), Integer.toString(book.getLength()), book.getTags());
        book = new Book("Maailmanlopun ravintola", "Adams, Douglas", 222, List.of("osa2", "liftausta"));
        db.addBook(book.getTitle(), book.getAuthor(), Integer.toString(book.getLength()), book.getTags());
        
        db.addBook("Taikavuori", "Thomas Mann", "800", List.of("romaani", "Hans Castorp"));
        db.addBook("Ulysses", "James Joyce", "111", List.of("Irlanti", "Leopold", "romaani"));
        db.addBook("Malleus Maleficarum", "The Spanish Inquisition", "612", List.of("ohjekirja", "noidat"));
        db.addBook("Harry Potter and The Prisoner Of Azkaban", "J. K. Rowling", "412", List.of("romaani", "noidat"));
    }
    
}
