package Data;

import DAO.EinladungDAO;
import DAO.TeilnehmerDAO;
import database.TimestampAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Termin {

        public int id;
        public String name;
        public Time von;
        public Time bis;
        public String beschreibung;
        public String ort;

        private Date date;

        //private TerminBox terminBox;


    public Termin() {}

    public Termin(int id, String name, Time von, Time bis, String ort, String beschreibung, Date date) {

        this.id = id;
        this.name = name;
        this.von = von;
        this.bis = bis;
        this.ort = ort;
        this.beschreibung = beschreibung;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "id=" + id +
                ", von=" + von +
                ", bis=" + bis +
                ", beschreibung='" + beschreibung + '\'' +
                ", ort='" + ort + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    /**
     * Methode um die eingeladenen Nutzer des Termins zurückzugeben
     * @return List<Nutzer> Liste der Teilnehmer
     */
    public List<Nutzer> getEingeladene(){

        EinladungDAO einladung = new EinladungDAO();

        return einladung.getEingeladene(this);

    }

    /**
     * Methode um die Teilnehmer des Termins zurückzugeben
     * @return List<Nutzer> Liste der Teilnehmer
     */
    public List<Nutzer> getTeilnehmer(){

        TeilnehmerDAO teilnehmer = new TeilnehmerDAO();

        return teilnehmer.getTeilnehmer(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    public Time getVon() {
        /*Date dateFrom = terminBox.getFrom();
        Timestamp timestamp = new Timestamp(dateFrom.getTime());
        return timestamp;*/
        return von;
    }

    public void setVon(Time von) {
        this.von = von;
    }

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    public Time getBis() {
       /* Date dateUntil = terminBox.getUntil();
        Timestamp timestamp = new Timestamp(dateUntil.getTime());
        return timestamp;*/
       return bis;
    }

    public void setBis(Time bis) {
        this.bis = bis;
    }

    public String getOrt() {
        /*String str = terminBox.getWhere();
        return str;*/
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getBeschreibung() {
       /*String str = terminBox.getBeschreibung();
       return str;*/
       return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Termin termin = (Termin) o;
        return id == termin.id &&
                Objects.equals(von, termin.von) &&
                Objects.equals(bis, termin.bis) &&
                Objects.equals(beschreibung, termin.beschreibung) &&
                Objects.equals(ort, termin.ort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, von, bis, beschreibung, ort);
    }
}
