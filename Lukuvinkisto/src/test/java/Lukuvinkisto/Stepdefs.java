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
