package Lukuvinkisto.media;

import java.util.List;

public class Book extends Media {

    private int pages;
    private int id;
    public Book(int id, String title, String author, int pages, List<String> tags) {
        super(title, author, "", pages, tags);
        this.pages = pages;
        this.id = id;
    }
    
    public Book(String title, String author, int pages, List<String> tags) {
        super(title, author, "", pages, tags);
        this.pages = pages;
        this.id = -1;
    }

    @Override
    public String toString() {
        if (tags==null || tags.isEmpty()) {
            return author + " : "  + title + ", sivumäärä: " + length;
        }
        return author + " : "  + title + ", sivumäärä: " + length + ", Tagit: " + this.getTagString() + " "
                + "<form method=\"POST\" action=\"/muokkaakirjaa\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\">" +
                "        <input type=\"submit\" value=\"Muokkaa\" />\n" +
                  "<form>";
    }
    
    public int getPages() {
        return pages;
    }
   
}
