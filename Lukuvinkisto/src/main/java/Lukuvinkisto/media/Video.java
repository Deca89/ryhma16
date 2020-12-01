package Lukuvinkisto.media;

public class Video extends Media {
  
    public Video(String title, String link) {
        super(title, "", link, 0);
    }
    
    @Override
    public String toString() {
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>";
    }
}
