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
    Video video1 = new Video("Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams", "https://youtu.be/dPbr0v_V-cI");
    Video video2 = new Video("The Hitchhiker's Guide to the Galaxy read by Douglas Adams [Part 1 of 4]", "https://youtu.be/FmakHVY7xeU");
    
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
        boolean r = db.addVideo(video1.getTitle(), video1.getLink());
        assertTrue(r);
    } 

    @Test
    public void testVideoAddOkTitle() {
        db.addVideo(video1.getTitle(), video1.getLink());
        List<Media> list = db.listVideos(null);
        assertEquals(list.get(0).getTitle(),video1.getTitle());
    } 

    @Test
    public void testVideoAddOkLink() {
        db.addVideo(video1.getTitle(), video1.getLink());
        List<Media> list = db.listVideos(null);
        assertEquals(list.get(0).getLink(),video1.getLink());
    } 

    @Test
    public void testExistingVideoAddReturnsFalse() {
        db.addVideo(video1.getTitle(), video1.getLink());
        boolean r = db.addVideo(video1.getTitle(), video1.getLink());
        assertFalse(r);
    } 

    @Test
    public void testRemovingNonExistingVideoReturnsFalse() {
        db.addVideo(video1.getTitle(), video1.getLink());
        boolean r = db.removeVideo(video2);
        assertFalse(r);
    } 

    @Test
    public void testTwoVideosAdded() {
        db.addVideo(video1.getTitle(), video1.getLink());
        db.addVideo(video2.getTitle(), video2.getLink());
        List<Media> list = db.listVideos(null);
        assertEquals(list.size(),2);
    } 

    @Test
    public void testTwoVideosAddedOneRemoved() {
        db.addVideo(video1.getTitle(), video1.getLink());
        db.addVideo(video2.getTitle(), video2.getLink());
        db.removeVideo(video1);
        
        List<Media> list = db.listVideos(null);
        assertEquals(list.size(),1);
    } 

    @Test
    public void testVideosSearchOk() {
        db.addVideo(video1.getTitle(), video1.getLink());
        db.addVideo(video2.getTitle(), video2.getLink());
        
        List<Media> list = db.listVideos("MOORE");
        assertEquals(list.get(0).getTitle(),"Stephen Moore reads The Hitch-Hiker's Guide to the Galaxy by Douglas Adams");
    } 

}
