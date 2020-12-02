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

    public boolean remove(Video video) {
        return db.removeVideo(video);
    }

    public boolean add(Video video) {
        return db.addVideo(video.getTitle(), video.getLink(), video.getTags());
    }
}
