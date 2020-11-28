package Lukuvinkisto.media;

public class Book extends Media {

    public Book(String title, String author, int pages) {
        super(title, author, "", pages);
    }

    

    @Override
    public String toString() {
        return author + " : "  + title + ". sivumäärä: " + length;
    }
}
