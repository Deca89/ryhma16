package Lukuvinkisto.dao;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class DemodbTest {
    
    @Test
    public void TestDemodbCorrectTagCount() {
        DemoTietokanta.luo("testi");
        TietokantaDAO db = new TietokantaDAO("testi");
        int count = db.listAllTags().size();
        File file = new File("testi.db"); 
        file.delete(); 
        assertEquals(14, count);
    }
     
    @Test
    public void TestDemodbCorrectTagSearch() {
        DemoTietokanta.luo("testi");
        TietokantaDAO db = new TietokantaDAO("testi");
        int count = db.SearchByTag("liftausta").size();
        File file = new File("testi.db"); 
        file.delete(); 
        assertEquals(7, count);
    }
}
