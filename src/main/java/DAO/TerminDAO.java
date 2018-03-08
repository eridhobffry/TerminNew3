package DAO;

import database.ConnectionHelper;
import Data.Termin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TerminDAO {

    //TerminBox terminBox = new TerminBox();
    Termin termin;

    public TerminDAO(){
        termin = new Termin();
    }

    private ConnectionHelper connection = new ConnectionHelper();

    public ArrayList<Termin> findAll() {
        ArrayList<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        String sql = "SELECT * FROM Termin";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }

    public List<Termin> findByBeschreibung(String beschreibung) {
        List<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        String sql = "SELECT * FROM Termin WHERE Beschreibung LIKE '%'|| ? || '%'";
        try {
            c = ConnectionHelper.getConnection();
            // prepareStatement creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, beschreibung);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }

    public Termin findById(int id) {
        String sql = "SELECT * FROM Termin WHERE id = ?";
        Termin termin = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                termin = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }


   //
    public Termin create(Termin termin) {

        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO Termin (Beschreibung, Ort, Von, Bis) VALUES (?, ?, ?, ?)",
                    new String[] { "ID" });
            ps.setString(1, termin.getBeschreibung());
            ps.setString(2, termin.getOrt());
            ps.setTimestamp(3, termin.getVon());
            ps.setTimestamp(4, termin.getBis());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            termin.setId(id);
        /*try {
            if (date != null && title != null && date != null && von != null && bis != null) {
                c = ConnectionHelper.getConnection();
                ps = c.prepareStatement("INSERT INTO Termin (Title, Von, Bis, Ort, Beschreibung, Datum) VALUES (?, ?, ?, ?, ?, ?)",
                        new String[]{"ID"});
                ps.setString(1, title);
                ps.setTime(2, von);
                ps.setTime(3, bis);
                ps.setString(4, ort);
                ps.setString(5, beschreibung);
                ps.setDate(6, (java.sql.Date) date);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                // Update the id in the returned object. This is important as this value must be returned to the client.
                int id = rs.getInt(1);
                termin.setId(id);
                }
            int count = ps.executeUpdate();
            return count == 1;*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }

    /*public Termin save(Termin termin) {
        return termin.getId() > 0 ? update(termin) : create(termin);
    }*/


    public Termin update(Termin termin) {

        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement
                    ("UPDATE Termin SET Beschreibung =?, Ort =?, Von =?, Bis =? WHERE id =?");
            ps.setString(1, termin.getBeschreibung());
            ps.setString(2, termin.getOrt());
            ps.setTimestamp(3, termin.getVon());
            ps.setTimestamp(4, termin.getBis());
            ps.setInt(5, termin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }

    /**
    @return whether deleting the row was successful
    */
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM Termin WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }

    Termin processRow(ResultSet rs) throws SQLException {
        Termin termin = new Termin();
        termin.setId(rs.getInt("ID"));
        termin.setBeschreibung(rs.getString("Beschreibung"));
        termin.setOrt(rs.getString("Ort"));
        termin.setVon(rs.getTimestamp("Von"));
        termin.setBis(rs.getTimestamp("Bis"));


        return termin;
    }

    /*public ArrayList<Termin> getAppointments(Date date) {
        ArrayList<Termin> appointments = new ArrayList<Termin>();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Connection c = null;

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Termin WHERE  ")
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }

    }*/



}
