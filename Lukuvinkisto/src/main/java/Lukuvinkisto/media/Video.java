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
    
    @Override
    public String getAsListElement(){
        String katsottu = "";
        if (this.status != 1) {
            katsottu = "Katsottu";
        } else {
            katsottu = "Katsomatta";
        }
        if (tags==null || tags.isEmpty()) {
            return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>" + katsottu + " "
                + "<form method=\"POST\" action=\"/muokkaavideo\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\"/>" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
        }
        return "<a href=\"" + getLink() + "\">" + getTitle() + "<a>, Tagit: " + this.getTagString() + katsottu + " "
                + "<form method=\"POST\" action=\"/muokkaavideo\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\"/>" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
    }
    
    @Override
    public String toString() {
        String katsottu = "";
        if (this.status != 1) {
            katsottu = "Katsottu";
        } else {
            katsottu = "Katsomatta";
        }
        String muokkaus = " "
                + "<form method=\"POST\" action=\"/muokkaavideo\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\"/>" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
        if (tags==null || tags.isEmpty()) {
            return title + " : "  + link + katsottu + muokkaus;
        }
        return title + " : "  + link + ", Tagit: " + this.getTagString() + katsottu + " "
                + "<form method=\"POST\" action=\"/muokkaavideo\">\n" +
                "        <input type=\"hidden\" name=\"haettavaId\" id=\"haettavaId\" value=\"" + id + "\"/>" +
                "        <input type=\"submit\" name=\"muokkaavideo\" value=\"Muokkaa\" />\n" +
                  "</form>";
    }
}
