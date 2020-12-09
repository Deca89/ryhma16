Feature: As a user I want to be able to delete media

    Scenario: Deleting an existing book works
        Given Book is added with title "Testi" and author "Testinen" and page count 77
        Then  Book removal for "Testi" and "Testinen" is "true"
        
    Scenario: Deleting a nonexisting book does nothing
        Given Book is added with title "Testi" and author "Testinen" and page count 77
        Then  Book removal for "nobook" and "nobody" is "false"
        
    Scenario: Deleting an existing video works
        Given Video is added with title "rickroll" and link "youtube"
        Then  Video removal for "rickroll" is "true"
        
    Scenario: Deleting a nonexisting Article does nothing
        Given Video is added with title "rickroll" and link "youtube"
        Then  Video removal for "music" is "false"
        
    Scenario: Deleting an existing article works
        Given Article is added with title "testarticle" and link "medium"
        Then  Article removal for "testarticle" is "true"
        
    Scenario: Deleting a nonexisting article does nothing
        Given Article is added with title "testarticle" and link "medium"
        Then  Article removal for "wrongarticle" is "false"
