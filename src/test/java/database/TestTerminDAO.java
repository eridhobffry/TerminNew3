package database;

import DAO.TerminDAO;
import Data.Termin;
import org.junit.Before;
import org.junit.Test;

public class TestTerminDAO {

    TerminDAO terminDAO;

    @Before
    public void setUp() {
        terminDAO = new TerminDAO();
    }

    @Test
    public void testListAll() {
        for (Termin t : terminDAO.findAll()) {
            System.out.println(t);
        }
    }

    @Test
    public void testFindByBeschreibung() {
        for (Termin t : terminDAO.findByBeschreibung("MUSTER")) {
            System.out.println(t);
        }
    }
}
