package gui;

import Data.manager.NutzerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Registration {

        static JFrame frame;
        static JTextField textField;
        static JTextField textField_2;

        private static NutzerManager manager = new NutzerManager();

        /**
         * Launch the application.
         */
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Registration window = new Registration();
                        window.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * Create the application.
         */
        public Registration() {
            initialize();
        }

        /**
         * Initialize the contents of the frame.
         */
        public static void initialize() {
            frame = new JFrame();
            frame.setBounds(100, 100, 730, 489);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);

            textField = new JTextField();
            textField.setBounds(128, 28, 86, 20);
            frame.getContentPane().add(textField);
            textField.setColumns(10);

            JLabel lblName = new JLabel("Nachname");
            lblName.setBounds(65, 31, 46, 14);
            frame.getContentPane().add(lblName);

            /*JLabel lblVorname = new JLabel("Vorname");
            lblVorname.setBounds(210, 31, 46, 14);
            frame.getContentPane().add(lblVorname);

            textField_1 = new JTextField();
            textField_1.setBounds(275, 28, 86, 20);
            frame.getContentPane().add(textField_1);
            textField_1.setColumns(10);*/

            JLabel lblEmailId = new JLabel("Vorname");
            lblEmailId.setBounds(65, 115, 46, 14);
            frame.getContentPane().add(lblEmailId);

            textField_2 = new JTextField();
            textField_2.setBounds(128, 112, 247, 17);
            frame.getContentPane().add(textField_2);
            textField_2.setColumns(10);

            JButton btnClear = new JButton("Clear");

            btnClear.setBounds(312, 387, 89, 23);
            frame.getContentPane().add(btnClear);

            JLabel lblSex = new JLabel("Gender");
            lblSex.setBounds(65, 228, 46, 14);
            frame.getContentPane().add(lblSex);

            JLabel lblMale = new JLabel("Male");
            lblMale.setBounds(128, 228, 46, 14);
            frame.getContentPane().add(lblMale);

            JLabel lblFemale = new JLabel("Female");
            lblFemale.setBounds(292, 228, 46, 14);
            frame.getContentPane().add(lblFemale);

            final JRadioButton radioButton = new JRadioButton("");
            radioButton.setBounds(337, 224, 109, 23);
            frame.getContentPane().add(radioButton);

            final JRadioButton radioButton_1 = new JRadioButton("");
            radioButton_1.setBounds(162, 224, 109, 23);
            frame.getContentPane().add(radioButton_1);

            /*JLabel lblOccupation = new JLabel("Occupation");
            lblOccupation.setBounds(65, 288, 67, 14);
            frame.getContentPane().add(lblOccupation);

            final JComboBox<String> comboBox = new JComboBox<String>();
            comboBox.addItem("Select");
            comboBox.addItem("Business");
            comboBox.addItem("Engineer");
            comboBox.addItem("Doctor");
            comboBox.addItem("Student");
            comboBox.addItem("Others");
            comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                }
            });
            comboBox.setBounds(180, 285, 91, 20);
            frame.getContentPane().add(comboBox);*/


            JButton btnSubmit = new JButton("submit");

            btnSubmit.setBackground(Color.BLACK);
            btnSubmit.setForeground(Color.GRAY);
            btnSubmit.setBounds(65, 387, 89, 23);
            frame.getContentPane().add(btnSubmit);


            btnSubmit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if(textField.getText().isEmpty()||(textField_2.getText().isEmpty())||((radioButton_1.isSelected())&&(radioButton.isSelected()))/*||(comboBox.getSelectedItem().equals("Select"))*/)
                        JOptionPane.showMessageDialog(null, "Data Missing");
                    else {
                        Boolean validName = true;

                        String nachName = textField.getText();
                        String vorName = textField_2.getText();

                        // validate name
                        if (nachName == null || nachName.isEmpty()) {
                            validName = false;
                        }

                        // validate name
                        if (vorName == null || vorName.isEmpty()) {
                            validName = false;
                        }

                        if (validName) {
                            manager.addNutzer(vorName, nachName);
                        }

                        JOptionPane.showMessageDialog(null, "Data Submitted");
                        MainFrame window = new MainFrame();
                        window.setVisible(true);
                    }
                }
            });

            btnClear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textField_2.setText(null);
                    textField.setText(null);
                    //textField_1.setText(null);
                    radioButton.setSelected(false);
                    radioButton_1.setSelected(false);
                    //comboBox.setSelectedItem("Select");


                }
            });

        }

        public String getNachname(){
            String str = textField.getText();
            return str;
        }

        /*public String getVorname(){
            String str = textField_1.getText();
            return str;
        }*/

        public String getVorname(){
            String str = textField_2.getText();
            return str;
        }



}
