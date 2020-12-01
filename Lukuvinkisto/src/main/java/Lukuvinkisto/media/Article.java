package Lukuvinkisto.media;

public class Article extends Media {
  
    public Article(String title, String link) {
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
