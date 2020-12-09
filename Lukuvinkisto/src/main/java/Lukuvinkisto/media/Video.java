package Lukuvinkisto.media;

import java.util.List;

public class Video extends Media {
    private int id;
    
    public Video(String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
    }
    
    public Video(String title, String link, List<String> tags, int status) {
        super(title, "", link, 0, tags, status);
    }
    
    public Video(int id, String title, String link, List<String> tags) {
        super(title, "", link, 0, tags);
        this.id = id;
    }
    
    public Video(int id, String title, String link, List<String> tags, int status) {
        super(title, "", link, 0, tags, status);
        this.id = id;
    }
    public String getId(){
        return id + "";
    }
    
    @Override
    public String getAsListElement(){
        String katsottu = (this.status != 1) ? "Katsottu" : "Katsomatta";
              
        if (tags==null || tags.isEmpty()) {
            return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>" + katsottu + " "
                + "<form method=\"GET\" action=\"/video/" + id + "\">\n" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
        }
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>, Tagit: " + this.getTagString() + "; " + katsottu + " "
                + "<form method=\"GET\" action=\"/video/" + id + "\">\n" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
    }
    
    @Override
    public String toString() {
        String katsottu = (this.status != 1) ? "Katsottu" : "Katsomatta";
        if (tags==null || tags.isEmpty()) {
            return title + " : "  + link + katsottu;
        }
        return title + " : "  + link + ", Tagit: " + this.getTagString() + "; " + katsottu;
    }
}
