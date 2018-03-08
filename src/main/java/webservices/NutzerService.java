package webservices;

import DAO.EinladungDAO;
import DAO.NutzerDAO;
import DAO.TeilnehmerDAO;
import Data.Nutzer;
import Data.Termin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(NutzerService.webContextPath)
public class NutzerService {

    static final String webContextPath = "/nutzer";
    private NutzerDAO nutzerDAO;
    private EinladungDAO einladungDAO;
    private TeilnehmerDAO teilnehmerDAO;

    public NutzerService() {
        nutzerDAO = new NutzerDAO();
        einladungDAO = new EinladungDAO();
        teilnehmerDAO = new TeilnehmerDAO();
    }

    /**
     * Gibt den Nutzer nach dem Name an.
     * Wenn kein Name angegeben ist, listet es alle Nutzer aus.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Nutzer> find(@QueryParam("vorname") String vorname, @QueryParam("nachname") String nachname) {
        if (vorname == null || nachname == null) {
            return nutzerDAO.findAll();
        } else {
            return nutzerDAO.findByName(vorname, nachname);
        }
    }

    /**
     * Erstellt den Nutzer
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Nutzer createNutzer(Nutzer nutzer) {
        return nutzerDAO.create(nutzer);
    }

    /**
     * Gibt den Nutzer mit ID=nutzerID an
     */
    @GET
    @Path("{nutzerID}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Nutzer findById(@PathParam("nutzerID") int nutzerID) {
        return nutzerDAO.findById(nutzerID);
    }

    /**
     * Ändert den Nutzer
     */
    @PUT
    @Path("{nutzerID}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Nutzer updateNutzer(Nutzer nutzer) {
       return nutzerDAO.update(nutzer);
    }

    /**
     * Löscht den Nutzer
     */
    @DELETE
    @Path("{nutzerID}")
    @Produces({MediaType.TEXT_PLAIN})
    public String removeNutzer(@PathParam("nutzerID") int nutzerID) {
        return String.valueOf(nutzerDAO.remove(nutzerID));
    }

    /**
     * Gibt alle Einladungen des Nutzers an
     */
    @GET
    @Path("{nutzerID}/einladungen")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Termin> getEinladungen(@PathParam("nutzerID") int nutzerID) {
        return einladungDAO.getEinladungen(nutzerID);
    }

    /**
     * Gibt alle Termine des Nutzers an
     */
    @GET
    @Path("{nutzerID}/termine")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Termin> getTermine(@PathParam("nutzerID") int nutzerID) {
        return teilnehmerDAO.getTermine(nutzerID);
    }
}
