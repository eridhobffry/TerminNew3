package rest;

import client.NutzerHandle;
import Data.Nutzer;
import Data.Termin;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webservices.KalenderServer;

import javax.ws.rs.client.WebTarget;
import java.util.List;

public class NutzerServiceDemo {

    private HttpServer server;
    private WebTarget target;

    private NutzerHandle nutzerHandle;

    @Before
    public void setUp() {
        // start the server
        server = KalenderServer.startServer();
        // create the client
        nutzerHandle = new NutzerHandle();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void getAllNutzer() {
        List< Nutzer> nutzerList = nutzerHandle.findAll();

        for (Nutzer nutzer : nutzerList) {
            System.out.println(nutzer);
        }
    }

    @Test
    public void getSomeNutzer() {
        // NOTE: add nutzer Max Mustermann and Maximilian Musterman
        List< Nutzer> nutzerList = nutzerHandle.findByName("Max", "");

        for (Nutzer nutzer : nutzerList) {
            System.out.println(nutzer);
        }
    }

    @Test
    public void createNutzer() {
        Nutzer erstellterNutzer = nutzerHandle.create(new Nutzer("Peter", "Musterman"));
        System.out.println(erstellterNutzer);
    }

    @Test
    public void getNutzerByID() {
        Nutzer responseMsg = nutzerHandle.findById(1234);
        System.out.println(responseMsg);
    }

    @Test
    public void updateNutzer() {
        Nutzer nutzer = new Nutzer("Max", "Musterring");
        Nutzer createdNutzer = nutzerHandle.create(nutzer);
        System.out.println("Eingefuegter Nutzer: " + createdNutzer);
        createdNutzer.setNachname("Musterman");
        Nutzer updatedNutzer = nutzerHandle.update(createdNutzer);
        System.out.println("Geaenderter Nutzer: " + updatedNutzer);
    }

    @Test
    public void deleteNutzer() {
        Nutzer createdNutzer = nutzerHandle.create(new Nutzer());
        boolean responseMsg = nutzerHandle.remove(createdNutzer.getId());
        System.out.println(responseMsg);
    }

    @Test
    public void getEinladungen() {

        List<Termin> einladungenList = nutzerHandle.getEinladungen(nutzerHandle.findById(4361));

        for (Termin termin : einladungenList) {
            System.out.println(termin);
        }
    }

    @Test
    public void getTermine() {

        List<Termin> terminList = nutzerHandle.getTermine(nutzerHandle.findById(4359));

        for (Termin termin : terminList) {
            System.out.println(termin);
        }
    }
}
