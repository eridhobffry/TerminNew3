package Data;

import DAO.EinladungDAO;
import DAO.TeilnehmerDAO;
import gui.Registration;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Nutzer{

    private int id;
    private String vorname;
    private String nachname;

    private Registration registration;

    public Nutzer() {};

    public Nutzer(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Nutzer(int id, String vorname, String nachname) {

        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    /**
     * Methode um die Termine, in denen der Nutzer Teilnehmer ist, zurueckzugeben
     * @return Liste der Termine
     */
    public List<Termin> getTermine() {

        TeilnehmerDAO teilnehmerDAO = new TeilnehmerDAO();
        return teilnehmerDAO.getTermine(this);
    }

    /**
     * Methode um die Termine, zu denen der Nutzer eingeladet ist, zurueckzugeben
     * @return Liste der Termine
     */
    public List<Termin> getEinladungen() {

        EinladungDAO einladungDAO = new EinladungDAO();
        return einladungDAO.getEinladungen(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutzer nutzer = (Nutzer) o;
        return id == nutzer.id &&
                Objects.equals(vorname, nutzer.vorname) &&
                Objects.equals(nachname, nutzer.nachname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, vorname, nachname);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        /*String str = registration.getVorname();
        return str;*/
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {

        /*String str = registration.getNachname();
        return str;*/
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


    @Override
    public String toString() {
        return "Nutzer{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                '}';
    }
}
