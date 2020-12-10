Feature: As a user I want to be able to modify an existing book

    Scenario: Book's title is modified
        Given Book is added with title "Testi" and author "Testinen" and page count 77
		When the Book is then modified to have the title "TestiKirja"
        Then the title should be "TestiKirja"
        And  the author should be "Testinen"
	
	Scenario: Book's tags are modified
        Given Book is added with title "TagTesti" and author "Testinen" and page count 77
		When the Book's tags are then set to be "Tag1, Tag2, Tag3"
        Then the title should be "TagTesti"
		And  the Book's tags should be "Tag1, Tag2, Tag3"
		
	Scenario: Book's status is modified
		Given Book is added with title "StatusTesti" and author "Testinen" and page count 77
		When the Book's status is then set to 1
        Then the title should be "StatusTesti"
		And  the Book's status should be 1
		
	Scenario: Video's title is modified
        Given Video is added with title "Testi" and link "testi.linkki"
		When the Video is then modified to have the title "TestiVideo"
        Then the Video's title should be "TestiVideo"
	
	Scenario: Video's tags are modified
        Given Video is added with title "TagTesti" and link "testi.linkki"
		When the Video's tags are then set to be "Tag1, Tag2, Tag3"
		And  the Video's title should be "TagTesti"
		And  the Video's tags should be "Tag1, Tag2, Tag3"
		
	Scenario: Video's status is modified
		Given Video is added with title "StatusTesti" and link "testi.linkki"
		When the Video's status is then set to 1
		And  the Video's title should be "StatusTesti"
		And  the Video's status should be 1
		
	Scenario: Article's title is modified
        Given Article is added with title "Testi" and link "testi.linkki"
		When the Article is then modified to have the title "TestiVideo"
        Then the Article's title should be "TestiVideo"
	
	Scenario: Article's tags are modified
        Given Article is added with title "TagTesti" and link "testi.linkki"
		When the Article's tags are then set to be "Tag1, Tag2, Tag3"
		And  the Article's title should be "TagTesti"
		And  the Article's tags should be "Tag1, Tag2, Tag3"
		
	Scenario: Article's status is modified
		Given Article is added with title "StatusTesti" and link "testi.linkki"
		When the Article's status is then set to 1
		And  the Article's title should be "StatusTesti"
		And  the Article's status should be 1