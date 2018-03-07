package database;

import DAO.NutzerDAO;
import Data.Nutzer;
import org.junit.Before;
import org.junit.Test;

public class NutzerDAO_Demo {

    NutzerDAO nutzerDAO;

    @Before
    public void setUp() {
        nutzerDAO = new NutzerDAO();
    }

    @Test
    public void testListAll() {
        for (Nutzer p : nutzerDAO.findAll()) {
            System.out.println(p);
        }
    }

    @Test
    public void testFindByName() {
        for (Nutzer p : nutzerDAO.findByName("Gyorgyi", "Palatinus")) {
            System.out.println(p);
        }
    }

    @Test
    public void findByID() {
        System.out.println(nutzerDAO.findById(1234));
    }

}
