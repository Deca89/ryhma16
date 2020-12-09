package Lukuvinkisto.netio;

import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Media;
import Lukuvinkisto.media.Video;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author laaatte
 */
public class NVideoIO {

    private TietokantaDAO db;

    public NVideoIO(TietokantaDAO db) {
        this.db = db;
    }

    public List<Media> fetch() {
        List<Media> works = db.listVideos(null);
        Collections.sort(works);
        return works;
    }

    public List<Media> fetch(String input) {
        List<Media> works = db.listVideos(input);
        Collections.sort(works);
        return works;
    }
        
    public List<Media> fetchWithId(String input) {
        List<Media> work = db.getVideoById(input);
        return work;
    }

    public boolean remove(String title) {
        return db.removeVideo(title);
    }

    public boolean add(String title, String link, List<String> tags) {
        if (this.validate(title, link)) {
            return db.addVideo(title, link, tags);
        }
        return false;
    }
    
    public boolean modify(String id, String title, String link, List<String> tags, String status){
        if (this.validate(title, link)) {
            return db.modifyVideo(id, title, link, tags, status);
        }
        return false;
    }
    
    private boolean validate(String title, String link) {
        return title.length() > 2 && link.length() > 2;
    }
}
