package Data.manager;

import DAO.TerminDAO;
import Data.Termin;
import client.TerminHandle;
import org.glassfish.grizzly.http.util.TimeStamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


    /**
     * The <code>CalendarManager</code> manages communication between the different classes in the presentation, domain and data-storage level.
     * @version 1.0
     */
    public class CalendarManager {
        private TerminDAO appointment = new TerminDAO();

        /**
         * Gets all appointments of a given date.
         *  the date the appointments needs to be retrieved from
         * @return arraylist of appointments
         */
        public ArrayList<Termin> findAll() {
            return appointment.findAll();
        }

        /**
         * Inserts a new appointment in the database.
         *
         * @return Termin
         */
        public Boolean addAppointment(String title, Time von, Time bis, String ort, String beschreibung, Date date) {
            return appointment.create(title, von, bis, ort, beschreibung, date);
        }

        /**
         * Deletes an appointment from the database.
         * @param appointmentId the id of the appointment that needs to be removed
         * @return boolean
         */
        public boolean deleteAppointment(Integer appointmentId) {
            return appointment.remove(appointmentId);
        }
    }

