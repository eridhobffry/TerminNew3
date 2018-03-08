package gui;

import DAO.NutzerDAO;
import Data.Nutzer;
import client.NutzerHandle;
import org.glassfish.grizzly.http.server.HttpServer;
import webservices.KalenderServer;

import javax.swing.*;
import javax.ws.rs.client.WebTarget;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class Login {


    private JFrame frame;

    private JButton btnRegistration;
    private JButton btnExit;
    private JLabel lblLoginSystems;

    static Color accent = new Color(0, 188, 212);
    static Color bg = new Color(96, 125, 139);

    private HttpServer server;
    private WebTarget target;

    private NutzerDAO nutzerDAO;
    private NutzerHandle nutzerHandle;


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

        setUp();
        initialize();
    }


    public void setUp() {
        // start the server
        server = KalenderServer.startServer();
        // create the client
        nutzerHandle = new NutzerHandle();
    }

    public void tearDown() {
        server.stop();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame();
        frame.setBounds(800, 500, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Waehlen Sie Ihre Nachname & Vorname: ");
        lblUsername.setBounds(270, 105, 400, 80);
        frame.getContentPane().add(lblUsername);

        Nutzer erstellterNutzer = nutzerHandle.create(new Nutzer("Peter", "Musterman"));
        final JComboBox<Nutzer> comboBox = new JComboBox<>();
        comboBox.addItem(erstellterNutzer);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox.setBounds(220, 220, 340, 30);
        frame.getContentPane().add(comboBox);


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
        btnLogin.setBounds(250, 350, 89, 30);
        frame.getContentPane().add(btnLogin);

        btnRegistration = new JButton("Registration");
        btnRegistration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Registration window = new Registration();
                window.frame.setVisible(true);
            }
        });

        btnRegistration.setBounds(400, 350, 120, 30);
        frame.getContentPane().add(btnRegistration);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(38, 73, 700, 10);
        frame.getContentPane().add(separator_1);

        lblLoginSystems = new JLabel("Wilkommen im Termin Kalendar");
        lblLoginSystems.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblLoginSystems.setBounds(230, 18, 700, 31);
        frame.getContentPane().add(lblLoginSystems);
    }
}
