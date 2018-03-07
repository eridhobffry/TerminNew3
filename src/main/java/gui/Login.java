package gui;

import java.awt.*;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;

public class Login {


    private JFrame frame;
    //private JTextField textField;
    //private JTextField txtUsername;
    private JButton btnRegistration;
    private JButton btnExit;
    private JLabel lblLoginSystems;

    static Color accent = new Color(0, 188, 212);
    static Color bg = new Color(96, 125, 139);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login window = new Login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Font loadFont(String name, float size){
        InputStream is = Login.class.getResourceAsStream("/resources/"+name);
        Font title = null;
        try {
            title = Font.createFont(Font.TRUETYPE_FONT, is);
            title = title.deriveFont(size);
        } catch (FontFormatException ex) {ex.printStackTrace();
        }
        catch (IOException ex){ex.printStackTrace();}
        return title;
    }

    /**
     * Create the application.
     */
    public Login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame();
        frame.setBounds(600, 300, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username : ");
        lblUsername.setBounds(38, 74, 124, 30);
        frame.getContentPane().add(lblUsername);

        final JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem("Gyoergyipalatinus");
        comboBox.addItem("Eridhobuffery");
        comboBox.addItem("Enxhinina");
        comboBox.addItem("Peterparker");
        comboBox.addItem("Timoklein");
        comboBox.addItem("Others");
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox.setBounds(150, 120, 140, 30);
        frame.getContentPane().add(comboBox);

        /*JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(38, 74, 124, 30);
        frame.getContentPane().add(lblUsername);


        txtUsername = new JTextField();
        txtUsername.setBounds(162, 79, 221, 30);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);*/


        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {


                if(comboBox.getSelectedItem().equals("Select")/*txtUsername.getText().isEmpty()*/) {
                    JOptionPane.showMessageDialog(null, "Invalid Login Details",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    MainFrame window = new MainFrame();
                    window.setVisible(true);
                }
            }
        });
        btnLogin.setBounds(100, 199, 89, 30);
        frame.getContentPane().add(btnLogin);

        btnRegistration = new JButton("Registration");
        btnRegistration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Registration window = new Registration();
                window.frame.setVisible(true);
            }
        });

        btnRegistration.setBounds(200, 199, 120, 30);
        frame.getContentPane().add(btnRegistration);


        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(38, 53, 347, 10);
        frame.getContentPane().add(separator_1);

        lblLoginSystems = new JLabel("Termincalendar");
        lblLoginSystems.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblLoginSystems.setBounds(139, 11, 250, 31);
        frame.getContentPane().add(lblLoginSystems);
    }
}
