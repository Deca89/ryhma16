package Lukuvinkisto;

import Lukuvinkisto.media.Book;
import Lukuvinkisto.media.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class VideoTest {
    
    @Test
    public void ReturnCorrectTitle() {
        Video video = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd"));
        assertEquals(video.getTitle(), "Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams");
    }

    @Test
    public void ReturnCorrectLink() {
        Video video = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd"));
        assertEquals(video.getLink(), "https://youtu.be/dPbr0v_V-cI");
    }

    @Test
    public void ReturnCorrectTag() {
        Video video = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd"));
        assertEquals(video.getTags().get(1), "cd");
    }

    
    @Test
    public void SortsCorrectly() {
        ArrayList videos = new ArrayList<Book>();
        videos.add(new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd")));
        videos.add(new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU", List.of("ef", "gh")));
        Collections.sort(videos);
        assertEquals(videos.get(0).toString(), "Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams : https://youtu.be/dPbr0v_V-cI, Tagit: ab, cd");
    }
    @Test
    public void ReturnCorrectListElement() {
        Video video = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd"));
        assertEquals(video.getAsListElement(), "<a href=\"https://youtu.be/dPbr0v_V-cI\">Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams<a>, Tagit: ab, cd");
    }
}
