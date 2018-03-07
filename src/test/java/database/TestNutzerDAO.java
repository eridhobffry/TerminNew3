package database;

import DAO.NutzerDAO;
import Data.Nutzer;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestNutzerDAO {

    NutzerDAO nutzerDAO;

    @Before
    public void setUp() {
        nutzerDAO = new NutzerDAO();
    }

    /*@Test
    public void insertIntoEmptyDB() {

        removeAlleNutzer();
        Nutzer nutzer = new Nutzer("Max", "Musterman");
        Nutzer insertedNutzer = nutzerDAO.create(nutzer);
        List<Nutzer> list = nutzerDAO.findAll();

        assertEquals("Fehler: Es gibt keine oder mehr als einen Nutzer in der Datenbank",
                list.size(), 1);
        assertEquals("Fehler: Der erste Eintrag in der Datenbank ist nicht gleich mit den eingefügten Nutzer ",
               list.get(0), insertedNutzer);
    }*/

   /* @Test
    public void updateSingleNutzer() {
        removeAlleNutzer();
        String vorname = "max";
        String nachname = "muster";
        Nutzer nutzer = new Nutzer("Max", "Musterman");
        Boolean insertedNutzer = nutzerDAO.create(vorname, nachname);
        nutzer.setNachname("NewMax");
        nutzerDAO.update(insertedNutzer);

        List<Nutzer> list = nutzerDAO.findAll();
        assertEquals("Fehler: Es gibt keine oder mehr als einen Nutzer in der Datenbank",
                list.size(), 1);
        assertEquals("Fehler: Der erste Eintrag in der Datenbank ist nicht gleich mit dem geaenderten Nutzer",
                list.get(0), insertedNutzer);
    }*/

    @Test
    public void findByID() {
        removeAlleNutzer();
        String vorname = "max";
        String nachname = "muster";
        Nutzer nutzer = new Nutzer("Max", "Musterman");
        Boolean insertedNutzer = nutzerDAO.create(vorname, nachname);

        Nutzer nutzerById = nutzerDAO.findById(nutzer.getId());
        assertEquals("Fehler: Der mit der ID ausgesuchte Eintrag in der Datenbank ist nicht gleich mit dem eingefuegten Nutzer",
                nutzerById, insertedNutzer);

        int nichtExistierendesID = nutzer.getId() + 1;
        nutzerById = nutzerDAO.findById(nichtExistierendesID);
        assertEquals("Fehler: Aussuchen von nicht existierenden Eintrag ist nicht fehlgeschlagen.", null, nutzerById);
    }

    private void removeAlleNutzer() {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM Nutzer");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }
}
