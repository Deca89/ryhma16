
package Lukuvinkisto.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiedostoDAOTest {
    TiedostoDAO instance = new TiedostoDAO();
    String fileName = "TestFile";

    @After
    public void deleteOutputFile() {
          instance.deleteFile("TestFile");
    }
    
    /**
     * Test of createFile method, of class TiedostoDAO.
     */
    @Test
    public void testCreateFile() {
        instance.deleteFile("TestFile");
        Integer expResult = instance.createFile("TestFile");
        assertEquals((Object) expResult, (Object) 2);
        
        expResult = instance.createFile("TestFile");
        assertEquals((Object) expResult, (Object) 1);
    }

    /**
     * Test of fileExists method, of class TiedostoDAO.
     */
    @Test
    public void testFileExists() {
        boolean expResult = true;
        boolean result = instance.fileExists(fileName);
        assertEquals(expResult, result);
        
        instance.createFile(fileName);
        
        expResult = true;
        result = instance.fileExists(fileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteFile method, of class TiedostoDAO.
     */
    @Test
    public void testDeleteFile() {
        instance.createFile(fileName);
         
        boolean expResult = true;
        boolean result = instance.deleteFile(fileName);
        assertEquals(expResult, result);
    }
    
}
