package Lukuvinkisto.media;

import java.util.List;

public class Article extends Media {
    private int id;
    
    public Article(String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
    }
    
    public Article(int id, String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
        this.id = id;
    }
  
    public Article(int id, String title, String link, List<String> tags, int status) {
        super(title, "", link, 0, tags);
        this.id = id;
    }
  
    public Article(String title, String link, List<String> tags, int status) {
        super(title, "", link, 0, tags, status);
    }
    
    @Override
    public String getAsListElement(){
        String luettu = (status != 1)? "Luettu" : "Lukematta";
        String muokkaus = " "
                + "<form method=\"GET\" action=\"/artikkeli/" + id + "\">\n" +
                "        <input type=\"submit\" name=\"muokkaaartikkeli\" value=\"Muokkaa\" />\n" +
                  "</form>";
        if (tags==null || tags.isEmpty()) {
            return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>" + luettu + muokkaus;
        }
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>, Tagit: " + this.getTagString() + "; " + luettu + muokkaus;
    }
    
    @Override
    public String toString() {
        String luettu = (status != 1)? "Luettu" : "Lukematta";
        if (tags==null || tags.isEmpty()) {
            return title + " : "  + link + luettu;
        }
        return title + " : "  + link + ", Tagit: " + this.getTagString() + "; " + luettu;
    }
}
