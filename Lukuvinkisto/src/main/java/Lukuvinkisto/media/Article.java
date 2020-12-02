package Lukuvinkisto.media;

import java.util.List;

public class Article extends Media {
  
    public Article(String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
    }
    
    @Override
    public String toString() {
        return title + " : "  + link + ", Tagit: " + this.getTagString();
    }
}
