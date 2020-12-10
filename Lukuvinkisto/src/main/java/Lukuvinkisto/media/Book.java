package Lukuvinkisto.media;

import java.util.List;

public class Book extends Media {

    private int pages;
    private int id;
    public Book(int id, String title, String author, int pages, List<String> tags, int status) {
        super(title, author, "", pages, tags, status);
        this.pages = pages;
        this.id = id;
    }
    
    public Book(String title, String author, int pages, List<String> tags, int status) {
        super(title, author, "", pages, tags, status);
        this.pages = pages;
        this.id = -1;
    }
    
    public Book(int id, String title, String author, int pages, List<String> tags) {
        super(title, author, "", pages, tags, 0);
        this.pages = pages;
        this.id = id;
    }
    
    public Book(String title, String author, int pages, List<String> tags) {
        super(title, author, "", pages, tags, 0);
        this.pages = pages;
        this.id = -1;
    }
    public String getId(){
        return id + "";
    }
    @Override
    public String getAsListElement(){
        if (tags==null || tags.isEmpty()) {
            return author + " : "  + title + ", sivum‰‰r‰: " + length + ", luettu: " + status + "/" + length + " "
                + "<form method=\"GET\" action=\"/kirja/"+id +"\">\n" +
                "        <input type=\"submit\" name=\"muokkaakirjaa\" value=\"Muokkaa\" />\n" +
                  "</form>";
        }
        return author + " : "  + title + ", sivum‰‰r‰: " + length + ", luettu: " + status + "/" + length + ", Tagit: " + this.getTagString() + " "
                + "<form method=\"GET\" action=\"/kirja/"+id +"\">\n" +
                "        <input type=\"submit\" name=\"muokkaakirjaa\" value=\"Muokkaa\" />\n" +
                  "</form>";
    }
    
    @Override
    public String toString() {
        if (tags==null || tags.isEmpty()) {
            return author + " : "  + title + ", sivum‰‰r‰: " + length + ", luettu: " + status + "/" + length;
        }
        return author + " : "  + title + ", sivum‰‰r‰: " + length + ", luettu: " + status + "/" + length + ", Tagit: " + this.getTagString();
    }
    
    public int getPages() {
        return pages;
    }
   
}
