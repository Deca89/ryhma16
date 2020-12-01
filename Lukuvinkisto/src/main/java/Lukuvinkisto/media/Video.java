package Lukuvinkisto.media;

public class Video extends Media {
  
    public Video(String title, String link) {
        super(title, "", link, 0);
    }
    
    @Override
    public String getAsListElement(){
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>";
    }
    
    @Override
    public String toString() {
        return title + " : "  + link;
    }
}
