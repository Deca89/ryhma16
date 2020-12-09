package Lukuvinkisto;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.*;
import Lukuvinkisto.netio.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import static org.junit.Assert.*;

public class Stepdefs {

    Book book;
    Video video;
    Article article;
    Queue<String> inputs;
    TiedostoDAO db;
    Integer a;
    TietokantaDAO instance;
    NArticleIO nArticle;
    NBookIO nBook;
    NVideoIO nVideo;
    boolean validateResult;

    @Before
    public void initialize() {
        db = new TiedostoDAO();
        a = db.createFile("TestDatabase");
        instance = new TietokantaDAO("TestDatabase");

        nArticle = new NArticleIO(instance);
        nBook = new NBookIO(instance);
        nVideo = new NVideoIO(instance);
    }

    @Given("Books, videos and articles in demodatabase are added to database")
    public void demodbAdded() {
        Article article = new Article("The Hitchhiker's Guide to the Galaxy", "https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy", List.of("wikipedia", "englanti", "liftausta"));
        instance.addArticle(article.getTitle(), article.getLink(), article.getTags());
        article = new Article("Linnunradan käsikirja liftareille", "https://fi.wikipedia.org/wiki/Linnunradan_k%C3%A4sikirja_liftareille", List.of("wikipedia", "liftausta"));
        instance.addArticle(article.getTitle(), article.getLink(), article.getTags());
        instance.addArticle("The Game - 30th Anniversary Edition", "https://www.bbc.co.uk/programmes/articles/1g84m0sXpnNCv84GpN2PLZG/the-game-30th-anniversary-edition", List.of("peli", "liftausta"));
        
        Video video = new Video("Hitchhiker's Guide - Earth Destroyed and Guide Introduction (HD)", "https://youtu.be/Z1Ba4BbH0oY", List.of("elokuva", "liftausta"));
        instance.addVideo(video.getTitle(), video.getLink(), video.getTags());
        video = new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU", List.of("äänikirja", "englanti", "osa1","liftausta"));
        instance.addVideo(video.getTitle(), video.getLink(), video.getTags());

        Book book = new Book("Linnunradan käsikirja liftareille", "Adams, Douglas", 203, List.of("osa1", "liftausta"));
        instance.addBook(book.getTitle(), book.getAuthor(), Integer.toString(book.getLength()), book.getTags());
        book = new Book("Maailmanlopun ravintola", "Adams, Douglas", 222, List.of("osa2", "liftausta"));
        instance.addBook(book.getTitle(), book.getAuthor(), Integer.toString(book.getLength()), book.getTags());
        
        instance.addBook("Taikavuori", "Thomas Mann", "800", List.of("romaani", "Hans Castorp"));
        instance.addBook("Ulysses", "James Joyce", "111", List.of("Irlanti", "Leopold", "romaani"));
        instance.addBook("Malleus Maleficarum", "The Spanish Inquisition", "612", List.of("ohjekirja", "noidat"));
        instance.addBook("Harry Potter and The Prisoner Of Azkaban", "J. K. Rowling", "412", List.of("romaani", "noidat"));
    }
    
    @Given("Book is added with title {string} and author {string} and page count {int} and tags {string}")
    public void bookIsInitializedWithTags(String title, String author, int pagecount, String tagstr) {
        List<String> tagit = new ArrayList();
        Collections.addAll(tagit,tagstr.split(","));
        nBook.add(title, author, Integer.toString(pagecount), tagit);
    }

    @Given("Video is added with title {string} and link {string} and tags {string}")
    public void videoIsInitializedWithTags(String title, String link, String tagstr) {
        List<String> tagit = new ArrayList();
        Collections.addAll(tagit,tagstr.split(","));
        nVideo.add(title, link, tagit);
    }

    @Given("Article is added with title {string} and link {string} and tags {string}")
    public void articleIsInitializedWithTags(String title, String link, String tagstr) {
        List<String> tagit = new ArrayList();
        Collections.addAll(tagit,tagstr.split(","));
        nVideo.add(title, link, tagit);
    }
    
    @Given("Book is initialized with title {string} and author {string} and page count {int}")
    public void bookIsInitialized(String title, String author, int pagecount) {
        book = new Book(title, author, pagecount, null);
    }

    @Given("Video is initialized with title {string} and link {string}")
    public void videoIsInitialized(String title, String link) {
        video = new Video(title, link, null);
    }

    @Given("Article is initialized with title {string} and link {string}")
    public void articleIsInitialized(String title, String link) {
        article = new Article(title, link, null);
    }

    @Then("The search with tag {string} should return {int} items")
    public void TagSearchReturnsSeveralItems(String tag, int count) {
        assertEquals(count, instance.SearchByTag(tag).size());
    }

    @Then("The search with tag {string} should return item with title {string}")
    public void TagSearchForOne(String tag, String title) {
        assertEquals(title, instance.SearchByTag(tag).get(0).getTitle());
    }
    
    @Then("the author should be {string}")
    public void theAuthorShouldBe(String val) {
        assertEquals(val, book.getAuthor());
    }

    @Then("the title should be {string}")
    public void theTitleShouldBe(String val) {
        assertEquals(val, book.getTitle());
    }

    @Then("the pagecount should be {int}")
    public void thePagecountShouldBe(int val) {
        assertEquals(val, book.getLength());
    }

    @Then("Book add validation is {string}")
    public void bookValidationReturns(String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nBook.add(book.getTitle(), book.getAuthor(), String.valueOf(book.getPages()), null)));
    }

    @Then("Video add validation is {string}")
    public void videoAddValidationReturns(String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nVideo.add(video.getTitle(), video.getLink(), null)));
    }

    @Then("Article add validation is {string}")
    public void articleAddValidationReturns(String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nArticle.add(article.getTitle(), article.getLink(), null)));
    }

    @Given("Book is added with title {string} and author {string} and page count {int}")
    public void bookIsAdded(String title, String author, int pagecount) {
        nBook.add(title, author, String.valueOf(pagecount), null);
    }

    @Then("Book search for {string} should return {int} results")
    public void bookSearchShouldReturnResults(String searchTerm, Integer results) {
        List<Media> found;
        if (searchTerm.length() == 0) {
            found = nBook.fetch();
        } else {
            found = nBook.fetch(searchTerm);
        }
        assertEquals(results, (Integer) found.size());
    }

    @Given("Video is added with title {string} and link {string}")
    public void videoIsAddedWithTitleAndLink(String title, String link) {
        nVideo.add(title, link, null);
    }

    @Then("Video search for {string} should return {int} results")
    public void videoSearchShouldReturnResults(String searchTerm, Integer results) {
        List<Media> found;
        if (searchTerm.length() == 0) {
            found = nVideo.fetch();
        } else {
            found = nVideo.fetch(searchTerm);
        }
        assertEquals(results, (Integer) found.size());
    }

    @Given("Article is added with title {string} and link {string}")
    public void articleIsAddedWithTitleAndLink(String title, String link) {
        nArticle.add(title, link, null);
    }

    @Then("Article search for {string} should return {int} results")
    public void articleSearchShouldReturnResults(String searchTerm, Integer results) {
        List<Media> found;
        if (searchTerm.length() == 0) {
            found = nArticle.fetch();
        } else {
            found = nArticle.fetch(searchTerm);
        }
        assertEquals(results, (Integer) found.size());
    }

    @Then("Video removal for {string} is {string}")
    public void videoRemovalIs(String title, String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nVideo.remove(title)));
    }

    @Then("Article removal for {string} is {string}")
    public void articleRemovalIs(String title, String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nArticle.remove(title)));
    }

    @Then("Book removal for {string} and {string} is {string}")
    public void bookRemovalIs(String title, String author, String trueFalse) {
        assertEquals(trueFalse, String.valueOf(nBook.remove(title, author)));
    }

}
