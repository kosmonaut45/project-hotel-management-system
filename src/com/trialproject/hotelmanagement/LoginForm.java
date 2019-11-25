package com.trialproject.hotelmanagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame{
    private JPanel mainPanel;
    private JTextField textFieldUserName;
    private JButton buttonLogin;
    private JPasswordField textFieldPassword;
    private JLabel labelPassword;
    private JLabel labelUserName;
    private JLabel labelUserLogin;
    private JPanel panelUserLogin;
    private JPanel panelBackground;

    private LoginForm() {
        buttonLogin.addActionListener(new LoginBtnClicked());
    }

    private class LoginBtnClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            PreparedStatement ps;
            ResultSet rs;

            String username = textFieldUserName.getText();
            String password = String.valueOf(textFieldPassword.getPassword());

            if (username.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Norėdami prisijungti įveskite vartotojo vardą", "Tuščias laukelis: vartotojo vardas", 2);
            } else if (password.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Norėdami prisijungti įveskite slaptažodį", "Tuščias laukelis: slaptažodis", 2);
            } else {

                MY_CONNECTION myConnection = new MY_CONNECTION();
                String selectQuery = "SELECT * FROM `users` WHERE `username` =? AND `password` =?";
                try {

                    ps = myConnection.createConnection().prepareStatement(selectQuery);

                    ps.setString(1, username);
                    ps.setString(2, password);

                    rs = ps.executeQuery();

                    if (rs.next()) {
                        MainForm mainForm = new MainForm();
                        mainForm.setVisible(true);
                        mainForm.setLocationRelativeTo(null);
                        mainForm.pack();
                        mainForm.setSize(400, 400);

                        //((JFrame) SwingUtilities.getWindowAncestor(mainPanel)).dispose();
                        SwingUtilities.getWindowAncestor(mainPanel).dispose();
                    } else {

                        JOptionPane.showMessageDialog(rootPane, "Neteisingas varototojo vardas arba slaptažodis", "Prisijungti nepavyko", 2);

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public static void main(String... args){
        JFrame frame = new JFrame("Prisijungimo forma");
        frame.setContentPane(new LoginForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}