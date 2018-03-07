package DAO;

import database.ConnectionHelper;
import Data.Nutzer;
import Data.Termin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EinladungDAO {

    /**
     * Die Methode erstellt eine Einladung
     * @param wer Nutzer, der anderen zum Termin einlädt
     * @param wen der Eingeladene
     * @param termin
     */
    public void create(Nutzer wer, Nutzer wen, Termin termin){
        create(wer.getId(), wen.getId(), termin.getId());
    }

    /**
     * Die Methode erstellt eine Einladung
     * @param wer Nutzer, der anderen zum Termin einlädt
     * @param wen der Eingeladene
     * @param termin
     */
    public void create(int wer, int wen, int termin){

        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO Einladungen(WER, WEN, TERMIN) VALUES (?, ?, ?)");
            ps.setInt(1, wer);
            ps.setInt(2, wen);
            ps.setInt(3, termin);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }

    /**
     * Die Methode löscht die Einladung, die zum Eingeladene geschickt wurde
     * @param wen die eingeladete Person
     * @return
     */
    public boolean remove(Nutzer wen, Termin termin){
        return remove(wen.getId(), termin.getId());
    }

    /**
     * Die Methode löscht die Einladung, die zum Eingeladene geschickt wurde
     * @param wen die eingeladete Person
     * @return
     */
    public boolean remove(int wen, int termin) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement
                    ("DELETE FROM Einladungen WHERE WEN =? AND TERMIN = ?");
            ps.setInt(1, wen);
            ps.setInt(2, termin);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }

    /**
     * Durch die Methode wird der Eingeladene ein Teilnehmer des Termins sein.
     * Der Nutzer soll aus der Liste der Eingeladene geloescht werden.
     * @param eingeladene die eingeladete Person
     * @param termin
     */
    public void annehmen(Nutzer eingeladene, Termin termin){
        annehmen(eingeladene.getId(), termin.getId());
    }

    /**
     * Durch die Methode wird der Eingeladene ein Teilnehmer des Termins sein.
     * Der Nutzer soll aus der Liste der Eingeladene geloescht werden.
     * @param eingeladene die eingeladete Person
     * @param termin
     */
    public void annehmen(int eingeladene, int termin){

        new TeilnehmerDAO().create(eingeladene, termin);

        remove(eingeladene, termin);
    }

    /**
     * Die Methode löscht den Eingeladene, der die Einladung abgelehnt hat.
     * @param eingeladene die eingeladete Person
     * @param termin
     */
    public void ablehnen(Nutzer eingeladene, Termin termin) {
        ablehnen(eingeladene.getId(), termin.getId());
    }

    /**
     * Die Methode löscht den Eingeladene, der die Einladung abgelehnt hat.
     * @param eingeladene die eingeladete Person
     * @param termin
     */
    public void ablehnen(int eingeladene, int termin) {

        remove(eingeladene,termin);
    }

    /**
     * Methode um die Nutzer, die fuer einen Termin eingeladet sind, zurueckzugeben
     * @param termin
     * @return List<Nutzer> die Liste der eingeladenen Nutzer
     */
    public List<Nutzer> getEingeladene(Termin termin){
        return getEingeladene(termin.getId());
    }

    /**
     * Methode um die Nutzer, die fuer einen Termin eingeladet sind, zurueckzugeben
     * @param termin
     * @return List<Nutzer> die Liste der eingeladenen Nutzer
     */
    public List<Nutzer> getEingeladene(int termin) {
        List<Nutzer> list = new ArrayList<Nutzer>();
        Connection c = null;
        NutzerDAO nutzer = new NutzerDAO();
        String sql = "SELECT NUTZER.* FROM NUTZER JOIN EINLADUNGEN WHERE " +
                "WEN = ID  AND TERMIN = ?";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, termin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(nutzer.processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;

    }

    /**
     * Methode um die Einladungen der Nutzer zurueckzugeben
     * @param nutzer der Nutzer
     * @return List<Termin> die Liste Termine
     */
    public List<Termin> getEinladungen(Nutzer nutzer){
        return getEinladungen(nutzer.getId());
    }

    /**
     * Methode um die Einladungen der Nutzer zurueckzugeben
     * @param nutzerId ID des Nutzers
     * @return List<Termin> die Liste Termine
     */
    public List<Termin> getEinladungen(int nutzerId) {
        List<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        TerminDAO termin = new TerminDAO();
        String sql = "SELECT TERMIN.* FROM TERMIN JOIN EINLADUNGEN WHERE " +
                "TERMIN = ID AND WEN = ?";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nutzerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(termin.processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }

}





