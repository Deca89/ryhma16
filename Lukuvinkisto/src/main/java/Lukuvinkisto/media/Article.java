package Lukuvinkisto.media;

import java.util.List;

public class Article extends Media {
  
    public Article(String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
    }
    
    @Override
    public String getAsListElement(){
        if (tags==null || tags.isEmpty()) {
            return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>";
        }
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>, Tagit: " + this.getTagString();
    }
    
    @Override
    public String toString() {
        if (tags==null || tags.isEmpty()) {
            return title + " : "  + link;
        }
        return title + " : "  + link + ", Tagit: " + this.getTagString();
    }
}
