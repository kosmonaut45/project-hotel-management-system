package com.trialproject.hotelmanagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JMenu manageMenu;
    private JMenuBar menuBar;
    private JMenuItem menuItemClients;
    private JMenuItem menuItemReservations;
    private JMenuItem menuItemRooms;

    MainForm(){
        setContentPane(mainPanel);
        setSize(400, 200);

        menuItemClients.addActionListener(new ClientsMenuItemClicked());
        menuItemReservations.addActionListener(new ReservationsMenuItemClicked());
        menuItemRooms.addActionListener(new RoomsMenuItemClicked());

    }

    private class ClientsMenuItemClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ManageClientsForm clientsForm = new ManageClientsForm();
            clientsForm.setVisible(true);
            clientsForm.pack();
            clientsForm.setLocationRelativeTo(null);
            clientsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
    }

    private class ReservationsMenuItemClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ManageReservationsForm reservationsForm = new ManageReservationsForm();
            reservationsForm.setVisible(true);
            reservationsForm.pack();
            reservationsForm.setLocationRelativeTo(null);
            reservationsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
    }

    private class RoomsMenuItemClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ManageRoomsForm roomsForm = new ManageRoomsForm();
            roomsForm.setVisible(true);
            roomsForm.pack();
            roomsForm.setLocationRelativeTo(null);
            roomsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
    }

}
