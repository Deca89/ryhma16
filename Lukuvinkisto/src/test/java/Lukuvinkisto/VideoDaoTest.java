package Lukuvinkisto;

import Lukuvinkisto.dao.TiedostoDAO;
import Lukuvinkisto.dao.TietokantaDAO;
import Lukuvinkisto.media.Media;
import Lukuvinkisto.media.Video;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VideoDaoTest {
    TietokantaDAO db;
    Video video1 = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI", List.of("ab", "cd"));
    Video video2 = new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU", List.of("ef", "gh"));
    
    @Before
    public void setUp() {
        TiedostoDAO dbFile = new TiedostoDAO();
        dbFile.createFile("testi");
        db = new TietokantaDAO("testi");

    }
    
    @After
    public void tearDown() {
        File file = new File("testi.db"); 
        file.delete(); 
    }

    @Test
    public void testVideoAddOk() {
        boolean r = db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        assertTrue(r);
    } 

    @Test
    public void testVideoAddOkTitle() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        List<Media> list = db.listVideos(null);
        assertEquals(list.get(0).getTitle(),video1.getTitle());
    } 

    @Test
    public void testVideoAddOkLink() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        List<Media> list = db.listVideos(null);
        assertEquals(list.get(0).getLink(),video1.getLink());
    } 

    @Test
    public void testExistingVideoAddReturnsFalse() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        boolean r = db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        assertFalse(r);
    } 

    @Test
    public void testRemovingNonExistingVideoReturnsFalse() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        boolean r = db.removeVideo(video2.getTitle());
        assertFalse(r);
    } 

    @Test
    public void testTwoVideosAdded() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        db.addVideo(video2.getTitle(), video2.getLink(), video2.getTags());
        List<Media> list = db.listVideos(null);
        assertEquals(list.size(),2);
    } 

    @Test
    public void testTwoVideosAddedOneRemoved() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        db.addVideo(video2.getTitle(), video2.getLink(), video2.getTags());
        db.removeVideo(video1.getTitle());
        
        List<Media> list = db.listVideos(null);
        assertEquals(list.size(),1);
    } 

    @Test
    public void testVideosSearchOk() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        db.addVideo(video2.getTitle(), video2.getLink(), video2.getTags());
        
        List<Media> list = db.listVideos("MOORE");
        assertEquals(list.get(0).getTitle(),"Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams");
    } 

    @Test
    public void testVideosTagsOk() {
        db.addVideo(video1.getTitle(), video1.getLink(), video1.getTags());
        db.addVideo(video2.getTitle(), video2.getLink(), video2.getTags());
        
        List<Media> list = db.listVideos("MOORE");
        System.out.println(list.get(0));
        assertEquals(list.get(0).getTags(), List.of("ab", "cd"));
    } 

}
