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
        if (tags==null || tags.isEmpty()) {
            return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>";
        }
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>, Tagit: " + this.getTagString();
    }
    
    @Override
    public String toString() {
        String luettu = "";
        String muokkaus = " "
                + "<form method=\"POST\" action=\"/muokkaakirjaa\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\"/>" +
                "        <input type=\"submit\" name=\"muokkaaartikkeli\" value=\"Muokkaa\" />\n" +
                  "</form>";
        if (this.status != 1) {
            luettu = "Luettu";
        } else {
            luettu = "Lukematta";
        }
        if (tags==null || tags.isEmpty()) {
            return title + " : "  + link + luettu + muokkaus;
        }
        return title + " : "  + link + ", Tagit: " + this.getTagString() + luettu + muokkaus;
    }
}
