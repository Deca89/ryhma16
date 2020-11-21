package Lukuvinkisto;

import Lukuvinkisto.io.InputStub;
import Lukuvinkisto.io.MediaStub;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.util.ArrayDeque;
import java.util.Queue;
import lukuvinkisto.io.InputIO;
import static org.junit.Assert.*;

public class Stepdefs {
    Book book;
    InputIO inputIO;
    Queue<String> inputs;
    
    @Given("Book is initialized")
    public void bookIsInitialized() {
        book = new Book("Testi", "Testinen",77);
    }
    
    @Given("{String} is inputted")
    public void firstInput(String val) {
        inputs = new ArrayDeque();
        inputIO = new InputIO(new MediaStub(), new InputStub(inputs));
        inputs.add(val);
    }
    @When("book with name of {String}, author of {String} and {int} pages is added")
    public void bookInputted(String name, String author, int pages){
        inputs.add(name);
        inputs.add(author);
        inputs.add(pages + "");
    }
    @When("{String} is inputted next")
    public void thenInputted(String val){
        inputs.add(val);
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
        assertEquals(val, book.getPages());
    }
    
    @Then("the system prints out {String}")
    public void checkPrint(String val){
        assertEquals(val, ((InputStub)inputIO.getInputIO()).getLastPrint());
    }
    
    

}