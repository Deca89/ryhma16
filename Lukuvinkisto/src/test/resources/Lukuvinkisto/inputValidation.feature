Feature: As a user I want my book input to be validated

    Scenario: Book input is correct
        Given Book is initialized with title "Testi" and author "Testinen" and page count 77
        Then  Book add validation is "true"
        
    Scenario: Title is too short
        Given Book is initialized with title "" and author "Testinen" and page count 77
        Then  Book add validation is "false"
        
    Scenario: Author name is too short
        Given Book is initialized with title "Testi" and author "" and page count 77
        Then  Book add validation is "false"
        
    Scenario: Page count is negative
        Given Book is initialized with title "Testi" and author "Testinen" and page count -2
        Then  Book add validation is "false"
        
    Scenario: Page count is zero
        Given Book is initialized with title "Testi" and author "Testinen" and page count 0
        Then  Book add validation is "false"
        
    Scenario: Video input is correct
        Given Video is initialized with title "testivideo" and link "testlink"
        Then  Video add validation is "true"
        
    Scenario: Video title is too short
        Given Video is initialized with title "" and link "testlink"
        Then  Video add validation is "false"
        
    Scenario: Video link is too short
        Given Video is initialized with title "testivideo" and link ""
        Then  Video add validation is "false"

    Scenario: Article input is correct
        Given Article is initialized with title "testarticle" and link "testlink"
        Then  Article add validation is "true"
        
    Scenario: Article title is too short
        Given Article is initialized with title "" and link "testlink"
        Then  Article add validation is "false"
        
    Scenario: Article link is too short
        Given Article is initialized with title "testarticle" and link ""
        Then  Article add validation is "false"
        
