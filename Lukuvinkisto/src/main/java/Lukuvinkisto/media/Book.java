package Lukuvinkisto.media;

import java.util.List;

public class Book extends Media {

    private int pages;
    public Book(String title, String author, int pages, List<String> tags) {
        super(title, author, "", pages, tags);
        this.pages = pages;
    }

    @Override
    public String toString() {
        if (tags==null || tags.isEmpty()) {
            return author + " : "  + title + ", sivumäärä: " + length;
        }
        return author + " : "  + title + ", sivumäärä: " + length + ", Tagit: " + this.getTagString();
    }
    
    public int getPages() {
        return pages;
    }
   
}
