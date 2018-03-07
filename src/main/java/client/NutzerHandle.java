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

public class NutzerHandle {

    private WebTarget target;

    public NutzerHandle() {
        Client c = ClientBuilder.newClient();
        target = c.target(KalenderServer.BASE_URI);
    }

    public List<Nutzer> findAll() {
        return target.path("nutzer").request(MediaType.APPLICATION_XML).get(new GenericType<List<Nutzer>>() {});
    }

    public List<Nutzer> findByName(String vorname, String nachname) {
        return target.path("nutzer")
                .queryParam("vorname", vorname)
                .queryParam("nachname", nachname)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Nutzer>>() {});
    }

    public Nutzer findById(int id) {
        return target.path("nutzer").path(String.valueOf(id))
                .request(MediaType.APPLICATION_XML).get(Nutzer.class);
    }

    public Nutzer create(Nutzer nutzer) {
        return target.path("nutzer").
                request(MediaType.APPLICATION_XML).
                post(Entity.entity(nutzer, MediaType.APPLICATION_XML), Nutzer.class);
    }

    public Nutzer save(Nutzer nutzer) {
        return nutzer.getId() > 0 ? update(nutzer) : create(nutzer);
    }


    public Nutzer update(Nutzer nutzer) {
        return target.path("nutzer").path(String.valueOf(nutzer.getId()))
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(nutzer, MediaType.APPLICATION_XML), Nutzer.class);
    }

    public boolean remove(int id) {
        String response = target.path("nutzer").path(String.valueOf(id))
                .request(MediaType.TEXT_PLAIN).delete(String.class);
        return Boolean.valueOf(response);
    }

    public List<Termin> getEinladungen(Nutzer nutzer) {
        return target.path("nutzer").path(String.valueOf(nutzer.getId())).path("einladungen")
                .request().accept(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Termin>>() {});
    }

    public List<Termin> getTermine(Nutzer nutzer) {
        return target.path("nutzer").path(String.valueOf(nutzer.getId())).path("termine").
                request().accept(MediaType.APPLICATION_XML).get(new GenericType<List<Termin>>() {});
    }
}

