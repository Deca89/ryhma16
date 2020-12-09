Feature: User can add tags to book, videos and articles and search with those tags

    Scenario: Search from several types of items ok
        Given Books, videos and articles in demodatabase are added to database
        Then  The search with tag "liftausta" should return 7 items

    Scenario: Tags added to book can be searched
        Given Book is added with title "abcdef" and author "xxx, yyy" and page count 123 and tags "eka, toka"
        Then  The search with tag "eka" should return item with title "abcdef"

    Scenario: Tags added to book can be searched by second tag
        Given Book is added with title "xxabcdef" and author "xxx, yyy" and page count 123 and tags "xeka, xtoka"
        Then  The search with tag "xtoka" should return item with title "xxabcdef"

    Scenario: Tags added to video can be searched
        Given Video is added with title "defgh" and link "xxx" and tags "kolmas, neljas"
        Then  The search with tag "kolmas" should return item with title "defgh"

    Scenario: Tags added to video can be searched by second tag
        Given Video is added with title "xxdefgh" and link "xxx" and tags "xkolmas, xneljas"
        Then  The search with tag "xneljas" should return item with title "xxdefgh"

    Scenario: Tags added to article can be searched
        Given Article is added with title "yydefgh" and link "xxx" and tags "ykolmas, yneljas"
        Then  The search with tag "kolmas" should return item with title "defgh"

    Scenario: Tags added to article can be searched by second tag
        Given Article is added with title "yyxxdefgh" and link "xxx" and tags "yxkolmas, yxneljas"
        Then  The search with tag "yxneljas" should return item with title "yyxxdefgh"