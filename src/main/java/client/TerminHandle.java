package client;

import Data.Nutzer;
import Data.Termin;
import webservices.KalenderServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class TerminHandle {

    private WebTarget target;

    public TerminHandle() {
        Client c = ClientBuilder.newClient();
        target = c.target(KalenderServer.BASE_URI);
    }

    public List<Termin> findAll() {
        return target.path("termine").request(MediaType.APPLICATION_XML).
                get(new GenericType<List<Termin>>() {});
    }

    public List<Termin> findByBeschreibung(String beschreibung) {
        return target.path("termine")
                .queryParam("beschreibung", beschreibung)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Termin>>() {});
    }

    public Termin findById(int id) {
        return target.path("termine").path(String.valueOf(id))
                .request().accept(MediaType.APPLICATION_XML).get(Termin.class);
    }

    public Termin create(Termin termin) {
        return target.path("termine").request(MediaType.APPLICATION_XML)
                .post(Entity.entity(termin, MediaType.APPLICATION_XML), termin.getClass());
    }

    public Termin save(Termin termin) {
        return termin.getId() > 0 ? update(termin) : create(termin);
    }


    public Termin update(Termin termin) {
        return target.path("termine").path(String.valueOf(termin.getId()))
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(termin, MediaType.APPLICATION_XML), termin.getClass());
    }

    public boolean remove(int id) {
        String response = target.path("termine").path(String.valueOf(id))
                .request(MediaType.TEXT_PLAIN).delete(String.class);
        return Boolean.valueOf(response);
    }

    public List<Nutzer> getEingeladene(Termin termin) {
        return target.path("termine").path(String.valueOf(termin.getId())).path("eingeladene")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Nutzer>>() {});
    }

    public List<Nutzer> getTeilnehmer(Termin termin) {
        return target.path("termine").path(String.valueOf(termin.getId())).path("teilnehmer")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Nutzer>>(){});
    }

    public void einladen(Termin termin, Nutzer wer, Nutzer wen) {
        target.path("termine").path(String.valueOf(termin.getId()))
                .path("einladen").queryParam("wer", wer.getId()).queryParam("wen", wen.getId())
                .request(MediaType.APPLICATION_XML)
                .post(Entity.xml(null));
    }

    public void annehmen(Termin termin, Nutzer eingeladene) {
        target.path("termine").path(String.valueOf(termin.getId()))
                .path("eingeladene").path(String.valueOf(eingeladene.getId()))
                .path("annehmen")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.xml(null));
    }

    public void ablehnen(Termin termin, Nutzer eingeladene) {
        target.path("termine").path(String.valueOf(termin.getId()))
                .path("eingeladene").path(String.valueOf(eingeladene.getId()))
                .request(MediaType.TEXT_PLAIN)
                .delete();
    }

    public void teilnehmerHinzufuegen(Termin termin, Nutzer teilnehmer) {
        target.path("termine").path(String.valueOf(termin.getId()))
                .path("teilnehmer").request(MediaType.APPLICATION_XML)
                .post(Entity.entity(teilnehmer, MediaType.APPLICATION_XML));
    }

    public void teilnahmeAbsagen(Termin termin, Nutzer eingeladene) {
        target.path("termine").path(String.valueOf(termin.getId()))
                .path("teilnehmer").path(String.valueOf(eingeladene.getId()))
                .request(MediaType.TEXT_PLAIN)
                .delete();
    }
}
