package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import DAO.TeilnehmerDAO;
import Data.Termin;
import Data.manager.CalendarManager;
import client.NutzerHandle;
import client.TerminHandle;
import org.glassfish.grizzly.http.util.TimeStamp;

/**
 * The <code>AppointmentPanel</code> ensures the panel of the <code>AppointmentFrame</code>.
 * Its shows the option to add an appointment and is placed within <code>AppointmentFrame</code>.
 * @author Bram de Hart
 * @version 1.0
 * @see TerminFrame
 */
public class TerminPanel extends JPanel {
    private Date date;
    private JFrame appointmentFrame;
    private CalendarPanel calendarPanel;
    private JTextField nameTextField, locationTextField, notesTextField, startTimeTextField, endTimeTextField;
    private Time formattedStartTime, formattedEndTime;
    private CalendarManager manager = new CalendarManager();
    private NutzerHandle nutzerHandle;

    public String title;
    public Time von;
    public Time bis;
    public String beschreibung;
    public String ort;

    private Date date1;

    /**
     * Constructor. Sets the global variables and calls the draw method.
     * @param month the month of the clicked daypanel
     * @param day the day of the clicked daypanel
     * @param year the year of the clicked daypanel
     * @param calendarPanel the calendarpanel the clicked daypabel is part of, to have access to its (parents) methods
     */
    public TerminPanel(Integer month, Integer day, Integer year, CalendarPanel calendarPanel, JFrame appointmentFrame) {
        this.calendarPanel = calendarPanel;
        this.appointmentFrame = appointmentFrame;
        this.date = calendarPanel.mainPanel.mainFrame.calendar.getDate(month, day, year);

        drawAppointmentPanel();
    }

    /**
     * Draws the appointment panel.
     */
    public void drawAppointmentPanel() {
        setLayout(new SpringLayout());
        String[] labels = {"Name", "Location", "Start time", "End time", "Notes", ""};
        int numPairs = labels.length;

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200,40));
        saveButton.addActionListener(new saveAppointmentHandler());

        ArrayList<JTextField> textFieldList = listTextFields();

        // fill the panel
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            add(l);
            if (i+1 < numPairs) {
                add(textFieldList.get(i));
            }
            else {
                add(saveButton);
            }
        }

        // lay out the panel
        SpringUtilities.makeCompactGrid(this,
                numPairs, 2, //rows, cols
                10, 10, //initX, initY
                10, 10 //xPad, yPad
        );
    }

    /**
     * List the textfields for use with the for-loop in <code>drawAppointments</code>.
     * @return ArrayList of textfields
     */
    private ArrayList<JTextField> listTextFields() {
        ArrayList<JTextField> textFieldList  = new ArrayList<>();
        textFieldList.add(nameTextField = new JTextField());
        textFieldList.add(locationTextField = new JTextField());
        textFieldList.add(startTimeTextField = new JTextField());
        textFieldList.add(endTimeTextField = new JTextField());
        textFieldList.add(notesTextField = new JTextField());

        return textFieldList;
    }

    /**
     * Shows an message dialog when the name of an event isn't filled in.
     */
    private void showNameError() {
        JOptionPane.showMessageDialog(null, "The name of the event must be filled in.", "Invalid name", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show an message dialog when the filled in times aren't valid.
     */
    private void showTimeError() {
        JOptionPane.showMessageDialog(null,
                "The start time or end time are invalid.\n" +
                        "Allowed format: (00 through 23) : (00 through 59).\n" +
                        "End time must be greater than start time.", "Invalid times",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an message dialog when an event is succesfully added.
     * @param name the name of the event.
     */
    private void showSuccesMessage(String name) {
        JOptionPane.showMessageDialog(null, "Your event \""+name+"\" is succesfully added.", "Event added", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Sets the global formatted time variables, based on a time string
     * @param time 4 digit time as a string
     * @param timeType 0 or 1; startTime or endTime
     * @return true or false; validated and setted or not
     */
    private Boolean setFormattedTime(String time, Integer timeType) {
        Boolean validated = true;
        Time formattedTime = new Time(new Date().getTime());

        // format time
        DateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            new SimpleDateFormat("HH:mm").parse(time);
            // good format
            formattedTime = new Time(formatter.parse(time).getTime());
        } catch (ParseException e) {
            // bad format
            validated = false;
        } finally {
            if (validated) {
                if (timeType == 0) {
                    // start time
                    formattedStartTime = formattedTime;
                }
                else if (timeType == 1){
                    // end time
                    formattedEndTime = formattedTime;
                }
            }
        }

        return validated;
    }

    /**
     * Inner class. Triggers an actionlistener when the <code>addAppointmentButton</code> is clicked.
     */
    class saveAppointmentHandler implements ActionListener {
        /**
         * Opens new frame where a new appointment can be added.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            TerminHandle terminHandle = null;
            Termin termin = terminHandle.create(new Termin());
            Boolean validName = true;
            Boolean validTimes = true;

            // get values
            String name = nameTextField.getText();
            String beschreibung = notesTextField.getText();
            String ort = locationTextField.getText();
            String von = startTimeTextField.getText(); // remove whitespace
            String bis = endTimeTextField.getText();

            // fields to null of not filled in
            if (beschreibung.isEmpty()) { beschreibung = null; }
            if (ort.isEmpty()) { ort = null; }

            // validate name
            if (name == null || name.isEmpty()) {
                validName = false;
            }
            // validate times
            if (!setFormattedTime(von, 0) || !setFormattedTime(bis, 1)) {
                validTimes = false;
            }
            if (validTimes) {
                // is end time greater then start time
                if (Integer.parseInt(von.replaceAll("[^\\d]","")) > Integer.parseInt(bis.replaceAll("[^\\d]",""))) {
                    validTimes = false;
                }
            }


            if (validName && validTimes) {
                // add appointment
                manager.addAppointment(termin);
                // close frame
                appointmentFrame.setVisible(false);
                appointmentFrame.dispose();
                // repaint panels and show succes message
                calendarPanel.monthPanel.redrawMonthPanel();
                showSuccesMessage(name);
            }
            else {
                // show errors
                if(!validName) { showNameError(); }
                if(!validTimes) { showTimeError(); }
            }
        }
    }
}
