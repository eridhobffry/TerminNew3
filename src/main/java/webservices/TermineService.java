package webservices;

import DAO.EinladungDAO;
import DAO.TeilnehmerDAO;
import DAO.TerminDAO;
import Data.Nutzer;
import Data.Termin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Path(TermineService.webContextPath)
public class TermineService {

    public int id;
    public Timestamp von;
    public Timestamp bis;
    public String beschreibung;
    public String ort;

    //private Date date;

    static final String webContextPath = "/termine";

    private TerminDAO terminDAO;
    private TeilnehmerDAO teilnehmerDAO;
    private EinladungDAO einladungDAO;

    public TermineService() {
        terminDAO = new TerminDAO();
        teilnehmerDAO = new TeilnehmerDAO();
        einladungDAO = new EinladungDAO();
    }

    /**
     * Erstellt einen Termin
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Termin create(Termin termin) {
        return terminDAO.create(termin);
    }

    /**
     * Gibt den Termin nach einer Beschreibung an.
     * Wenn keine Beschreibung angegeben wurde, listet es alle Termine aus
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Termin> find(@QueryParam("beschreibung") String beschreibung) {
        if (beschreibung == null) {
            return terminDAO.findAll();
        } else {
            return terminDAO.findByBeschreibung(beschreibung);
        }
    }

    /**
     * Gibt den Termin nach dem ID an
     */
    @GET
    @Path("{terminID}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Termin findById(@PathParam("terminID") int terminID) {
        return terminDAO.findById(terminID);
    }

    /**
     * Es ändert den Termin
     */
    @PUT
    @Path("{terminID}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Termin update(Termin termin) {
        return terminDAO.update(termin);
    }

    /**
     * Löscht den Termin
     */
    @DELETE
    @Path("{terminID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String remove(@PathParam("terminID") int terminID) {
        return String.valueOf(terminDAO.remove(terminID));
    }

    /**
     * Listet die Teilnehmer des Termins
     */
    @GET
    @Path("{terminID}/teilnehmer")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Nutzer> getTeilnehmer(@PathParam("terminID") int terminID) {
        return teilnehmerDAO.getTeilnehmer(terminID);
    }

    /**
     * Erstellt einen Teilnehmer zum Termin
     */
    @POST
    @Path("{terminID}/teilnehmer")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void createTeilnehmer(Nutzer nutzer, @PathParam("terminID") int terminID) {
        teilnehmerDAO.create(nutzer.getId(), terminID);
    }

    /**
     * Listen einen bestimmten Teilnehmer vom Termin
     */
    @GET
    @Path("{terminID}/teilnehmer/{teilnehmerID}")
    @Produces(MediaType.APPLICATION_XML)
    public Nutzer getTeilnehmerByID(@PathParam("terminID") int terminID, @PathParam("teilnehmerID") int teilnehmerID) {

        List<Nutzer> teilnehmer = teilnehmerDAO.getTeilnehmer(terminID);
        Nutzer result = null;
        for (Nutzer nutzer : teilnehmer) {
            if (nutzer.getId() == teilnehmerID) {
                result = nutzer;
            }
        }

        return result;
    }

    /**
     * Löscht den Teilnehmer vom Termin
     */
    @DELETE
    @Path("{terminID}/teilnehmer/{teilnehmerID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String removeTeilnehmer(@PathParam("terminID") int terminID, @PathParam("teilnehmerID") int teilnehmerID) {
        return String.valueOf(teilnehmerDAO.remove(teilnehmerID, terminID));
    }

    /**
     * Listet die Eingeladene des Termins
     */
    @GET
    @Path("{terminID}/eingeladene")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Nutzer> getEingeladene(@PathParam("terminID") int terminID) {
        return einladungDAO.getEingeladene(terminID);
    }

    /**
     * Eine Einladung annehmen: man wird vom Eingeladene zum Teilnehmer
     */
    @POST
    @Path("{terminID}/eingeladene/{nutzerID}/annehmen")
    public void annehmen(@PathParam("terminID") int terminID, @PathParam("nutzerID") int nutzerID) {
        einladungDAO.annehmen(nutzerID, terminID);
    }

    /**
     * Eine Einladung ablehnen: man wird aus der Liste der Eingeladene gelöscht
     */
    @DELETE
    @Path("{terminID}/eingeladene/{nutzerID}")
    public void ablehnen(@PathParam("terminID") int terminID, @PathParam("nutzerID") int nutzerID) {
         einladungDAO.ablehnen(nutzerID, terminID);
    }

    /**
     * Erstellt eine Einladung zum Termin
     */
    @POST
    @Path("{terminID}/eingeladene")
    public void createEinladung(
            @QueryParam("wer") int wer,
            @QueryParam("wen") int wen,
            @PathParam("terminID") int terminID) {

        einladungDAO.create(wer, wen, terminID);
    }

    /**
     * Erstellt eine Einladung zum Termin
     */
    @POST
    @Path("{terminID}/einladen")
    public void einladen(
            @QueryParam("wer") int wer,
            @QueryParam("wen") int wen,
            @PathParam("terminID") int terminID) {

        einladungDAO.create(wer, wen, terminID);
    }
}
