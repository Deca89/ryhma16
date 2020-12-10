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
        db.deleteFile("TestDatabase");
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
        video = new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU", List.of("Äänikirja", "englanti", "osa1","liftausta"));
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
        book = (Book)nBook.fetch(title).get(0);
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
        video = (Video) nVideo.fetch(title).get(0);
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
        article = (Article) nArticle.fetch(title).get(0);
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
    
    @When("the Book is then modified to have the title {string}")
    public void bookTitleModify(String newTitle) {
        nBook.modify(book.getId(), newTitle, book.getAuthor(), book.getPages() + "", book.getTags(), book.getStatus() + "");
        book = (Book)nBook.fetch(newTitle).get(0);
    }
    @When("the Book's tags are then set to be {string}")
    public void bookTagSet(String newTags) {
        
        String[] tagArray = newTags.split(", ");
        List<String> newTagList = new ArrayList<>();
        
        Collections.addAll(newTagList, tagArray);
        
        nBook.modify(book.getId(), book.getTitle(), book.getAuthor(), book.getPages() + "", newTagList, book.getStatus() + "");
        book = (Book)nBook.fetch(book.getTitle()).get(0);
    }
    
    @Then("the Book's tags should be {string}")
    public void checkBookTagsAreCorrect(String tagString){
        String[] tagArray = tagString.split(", ");
        List<String> tagList = new ArrayList<>();
        
        Collections.addAll(tagList, tagArray);
        
        assertTrue(tagList.size() == book.getTags().size());
        
        for (int i = 0; i < tagList.size(); i++) {
            assertTrue(tagList.get(i).equals(book.getTags().get(i)));
        }
        
    }
    
    @When("the Book's status is then set to {int}")
    public void bookStatusSet(int newStatus) {
        
        nBook.modify(book.getId(), book.getTitle(), book.getAuthor(), book.getPages() + "", book.getTags(), newStatus + "");
        book = (Book)nBook.fetch(book.getTitle()).get(0);
    }
    
    @Then("the Book's status should be {int}")
    public void bookStatusCheck(int value) {
        assertEquals(book.getStatus(), value);
    }
    
    @When("the Video is then modified to have the title {string}")
    public void videoTitleModify(String newTitle) {
        
        nVideo.modify(video.getId(), newTitle, video.getLink(), video.getTags(), video.getStatus() + "");
        video = (Video) nVideo.fetch(newTitle).get(0);
    }
    
    @When("the Video's tags are then set to be {string}")
    public void videoTagSet(String newTags) {
        
        String[] tagArray = newTags.split(", ");
        List<String> newTagList = new ArrayList<>();
        
        Collections.addAll(newTagList, tagArray);
        
        nVideo.modify(video.getId(), video.getTitle(), video.getLink(), newTagList, video.getStatus() + "");
        video = (Video)nVideo.fetch(video.getTitle()).get(0);
    }
    
    @Then("the Video's tags should be {string}")
    public void checkVideoTagsAreCorrect(String tagString){
        String[] tagArray = tagString.split(", ");
        List<String> tagList = new ArrayList<>();
        
        Collections.addAll(tagList, tagArray);
        
        assertTrue(tagList.size() == video.getTags().size());
        
        for (int i = 0; i < tagList.size(); i++) {
            assertTrue(tagList.get(i).equals(video.getTags().get(i)));
        }
        
    }
    
    @When("the Video's status is then set to {int}")
    public void videoStatusSet(int newStatus) {
        nVideo.modify(video.getId(), video.getTitle(), video.getLink(), video.getTags(), newStatus + "");
        video = (Video)nVideo.fetch(video.getTitle()).get(0);
    }
    
    @Then("the Video's status should be {int}")
    public void videoStatusCheck(int value) {
        assertEquals(value, video.getStatus());
    }
    
    @Then("the Video's title should be {string}")
    public void videoTitleCheck(String title){
        assertEquals(title, video.getTitle());
    }
    
    @When("the Article is then modified to have the title {string}")
    public void articleTitleModify(String newTitle) {
        
        nArticle.modify(article.getId(), newTitle, article.getLink(), article.getTags(), article.getStatus() + "");
        article = (Article) nArticle.fetch(newTitle).get(0);
    }
    
    @When("the Article's tags are then set to be {string}")
    public void articleTagSet(String newTags) {
        
        String[] tagArray = newTags.split(", ");
        List<String> newTagList = new ArrayList<>();
        
        Collections.addAll(newTagList, tagArray);
        
        nArticle.modify(article.getId(), article.getTitle(), article.getLink(), newTagList, article.getStatus() + "");
        article = (Article) nArticle.fetch(article.getTitle()).get(0);
    }
    
    @Then("the Article's tags should be {string}")
    public void checkArticleTagsAreCorrect(String tagString){
        String[] tagArray = tagString.split(", ");
        List<String> tagList = new ArrayList<>();
        
        Collections.addAll(tagList, tagArray);
        
        assertTrue(tagList.size() == article.getTags().size());
        
        for (int i = 0; i < tagList.size(); i++) {
            assertTrue(tagList.get(i).equals(article.getTags().get(i)));
        }
        
    }
    
    @When("the Article's status is then set to {int}")
    public void articleStatusSet(int newStatus) {
        nArticle.modify(article.getId(), article.getTitle(), article.getLink(), article.getTags(), newStatus + "");
        article = (Article) nArticle.fetch(article.getTitle()).get(0);
    }
    
    @Then("the Article's status should be {int}")
    public void articleStatusCheck(int value) {
        assertEquals(value, article.getStatus());
    }
    
    @Then("the Article's title should be {string}")
    public void articleTitleCheck(String title){
        assertEquals(title, article.getTitle());
    }
    
}
