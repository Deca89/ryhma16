Feature: As a user I can search media

    Scenario: Search without input returns all books
        Given Book is added with title "Testi" and author "Testinen" and page count 77
        Given Book is added with title "Testi2" and author "Testinen" and page count 77
        Then  Book search for "" should return 2 results
        
    Scenario: Search with search term returns matching books
        Given Book is added with title "Kirja" and author "Testinen" and page count 77
        Given Book is added with title "Opus" and author "Testinen" and page count 77
        Then  Book search for "Opus" should return 1 results
        
    Scenario: Search without input returns all videos
        Given Video is added with title "testivideo" and link "testlink"
        Given Video is added with title "rickroll" and link "youtube"
        Then  Video search for "" should return 2 results
        
    Scenario: Search with search term returns matching videos
        Given Video is added with title "testivideo" and link "testlink"
        Given Video is added with title "rickroll" and link "youtube"
        Then  Video search for "rickroll" should return 1 results
        
    Scenario: Search without input returns all articles
        Given Article is added with title "testarticle" and link "testlink"
        Given Article is added with title "how to java" and link "medium"
        Then  Article search for "" should return 2 results
        
    Scenario: Search without input returns all articles
        Given Article is added with title "testarticle" and link "testlink"
        Given Article is added with title "how to java" and link "medium"
        Then  Article search for "how" should return 1 results
