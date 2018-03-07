package rest;

import client.NutzerHandle;
import client.TerminHandle;
import Data.Nutzer;
import Data.Termin;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webservices.KalenderServer;

import javax.ws.rs.client.WebTarget;
import java.util.List;


public class TerminServiceDemo {

    private HttpServer server;
    private WebTarget target;

    private TerminHandle terminHandle;
    private NutzerHandle nutzerHandle;

    @Before
    public void setUp() {
        // start the server
        server = KalenderServer.startServer();
        // create the client
        terminHandle = new TerminHandle();
        nutzerHandle = new NutzerHandle();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void createTermin() {

        Termin termin = new Termin();
        Termin createdTermin = terminHandle.create(termin);
        System.out.println(createdTermin);
    }

    @Test
    public void getSomeTermin(){

        List<Termin> terminList = terminHandle.findByBeschreibung("MUSTER");

        for (Termin termin : terminList) {
            System.out.println(termin);
        }
    }

    @Test
    public void getAllTermin() {

        List<Termin> terminList = terminHandle.findAll();

        for (Termin termin : terminList) {
            System.out.println(termin);
        }

    }

    @Test
    public void getTerminByID() {
        Termin createdTermin = terminHandle.create(new Termin());
        Termin responseMsg = terminHandle.findById(createdTermin.getId());
        System.out.println(responseMsg);
    }

    @Test
    public void updateTermin() {
        Termin createdTermin = terminHandle.create(new Termin());
        createdTermin.setBeschreibung("Muster");
        Termin updatedTermin = terminHandle.update(createdTermin);
        System.out.println(updatedTermin);
    }

    @Test
    public void deleteTermin() {
        Termin createdTermin = terminHandle.create(new Termin());
        boolean response = terminHandle.remove(createdTermin.getId());
        System.out.println(response);
    }

    @Test
    public void getTeilnehmer() {

        List<Nutzer> teilnehmer = terminHandle.getTeilnehmer(terminHandle.findById(20));

        for (Nutzer nutzer : teilnehmer) {
            System.out.println(nutzer);
        }
    }

    @Test
    public void teilnehmerHinzufuegen() {
        Nutzer nutzer = nutzerHandle.create(new Nutzer());
        Termin termin = terminHandle.create(new Termin());
        terminHandle.teilnehmerHinzufuegen(termin, nutzer);
    }

    @Test
    public void absagen(){
        Nutzer nutzer = nutzerHandle.create(new Nutzer("abgesagte", "nutzer"));
        Termin termin = new Termin();
        termin.setBeschreibung("abgesagtes Termin");
        termin = terminHandle.create(termin);
        terminHandle.teilnehmerHinzufuegen(termin, nutzer);
        terminHandle.teilnahmeAbsagen(termin, nutzer);
    }

    @Test
    public void getEingeladene(){

        List<Nutzer> listEingeladene = terminHandle.getEingeladene(terminHandle.findById(20));

        for(Nutzer eingeladene: listEingeladene) {
            System.out.println(eingeladene);
        }
    }

    @Test
    public void annehmen(){
        Nutzer wer = nutzerHandle.create(new Nutzer("wer", "wer"));
        Nutzer wen = nutzerHandle.create(new Nutzer("wen", "wen"));
        Termin termin = new Termin();
        termin.setBeschreibung("einladung test");
        termin = terminHandle.create(termin);
        terminHandle.einladen(termin, wer, wen);
        terminHandle.annehmen(termin, wen);
    }

    @Test
    public void ablehnen() {
        Nutzer wer = nutzerHandle.create(new Nutzer("wer", "wer"));
        Nutzer wen = nutzerHandle.create(new Nutzer("wen", "wen"));
        Termin termin = new Termin();
        termin.setBeschreibung("einladung test");
        termin = terminHandle.create(termin);
        terminHandle.einladen(termin, wer, wen);
        terminHandle.ablehnen(termin, wen);
    }

    @Test
    public void einladen(){
        Nutzer wer = nutzerHandle.create(new Nutzer("wer", "wer"));
        Nutzer wen = nutzerHandle.create(new Nutzer("wen", "wen"));
        Termin termin = new Termin();
        termin.setBeschreibung("einladung test");
        termin = terminHandle.create(termin);
        terminHandle.einladen(termin, wer, wen);
    }

}
