package Lukuvinkisto.media;

import java.util.List;

public class Video extends Media {
  
    public Video(String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
    }
    
    @Override
    public String getAsListElement(){
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>";
    }
    
    @Override
    public String toString() {
        return title + " : "  + link + ", Tagit: " + this.getTagString();
    }
}
