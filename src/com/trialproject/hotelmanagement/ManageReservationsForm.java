package com.trialproject.hotelmanagement;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class ManageReservationsForm extends JFrame {
    private JPanel mainPanel;
    private JLabel labelMangeClients;
    private JLabel labelReservationID;
    private JTextField textFieldReservationID;
    private JLabel labelClientID;
    private JTextField textFieldClientID;
    private JLabel labelRoomNumber;
    private JTextField textFieldRoomNumber;
    private JLabel labelReservationBeginning;
    private JLabel labelReservationEnd;
    private JTable tableReservations;
    private JButton buttonAdd;
    private JButton buttonRemove;
    private JButton buttonEdit;
    private JButton buttonClearFields;
    private JButton buttonRefresh;
    private JPanel panelDateIn;
    private JPanel panelDateOut;
    private JPanel panelReservationTextBackground;
    private JPanel panelReservationFormBackground;
    private String[] columns = {"Rezervacijos nr.", "Kliento nr.", "Kambario nr.", "Pradžios data", "Pabaigos data"};

    private JDateChooser dateIn = new JDateChooser();
    private JDateChooser dateOut = new JDateChooser();
    private Reservation reservation = new Reservation();

    ManageReservationsForm() throws HeadlessException {
        setContentPane(mainPanel);

        dateIn.setDateFormatString("dd/MM/yyyy");
        panelDateIn.add(dateIn);
        dateOut.setDateFormatString("dd/MM/yyyy");
        panelDateOut.add(dateOut);

        buttonClearFields.addActionListener(new ManageReservationsForm.ClearButtonClicked());
        buttonAdd.addActionListener(new ManageReservationsForm.AddButtonClicked());
        buttonEdit.addActionListener(new ManageReservationsForm.EditButtonClicked());
        buttonRemove.addActionListener(new ManageReservationsForm.RemoveButtonCLicked());
        buttonRefresh.addActionListener(new ManageReservationsForm.RefreshButtonClicked());

        tableReservations.setModel(model);
        model.addRow(columns);
        reservation.fillReservationTableContents(tableReservations);

        tableReservations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel model = (DefaultTableModel) tableReservations.getModel();

                int rowIndex = tableReservations.getSelectedRow();

                try{
                    if(rowIndex != 0){
                        textFieldReservationID.setText(model.getValueAt(rowIndex, 0).toString());
                        textFieldClientID.setText(model.getValueAt(rowIndex, 1).toString());
                        textFieldRoomNumber.setText(model.getValueAt(rowIndex, 2).toString());

                        try {
                            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(rowIndex, 3).toString());
                            dateIn.setDate(startDate);
                            Date finishDate = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(rowIndex, 4).toString());
                            dateOut.setDate(finishDate);
                        } catch(ParseException ex){
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    //JOptionPane.showMessageDialog(rootPane, ex.getMessage() + "Pasirinkite rezervaciją", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private class ClearButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textFieldReservationID.setText("");
            textFieldClientID.setText("");
            textFieldRoomNumber.setText("");

            dateIn.setDate(new Date());
            dateOut.setDate(null);
        }
    }

    private class AddButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{

                int clientId = Integer.parseInt(textFieldClientID.getText());
                int roomNumber = Integer.parseInt(textFieldRoomNumber.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDate = dateFormat.format(dateIn.getDate());
                String finishDate = dateFormat.format(dateOut.getDate());
                String todayDate = dateFormat.format(new Date());

                Date start = dateFormat.parse(startDate);
                Date finish = dateFormat.parse(finishDate);
                Date today = dateFormat.parse(todayDate);

                if(!(start.after(today) || start.equals(today))){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite šiandienos arba būsimą rezervacijos pradžios datą", "Klaida", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(!(finish.after(start) || finish.equals(start))){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite būsimą rezervacijos pabaigos datą", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(reservation.addReservation(clientId, roomNumber, startDate, finishDate)){

                        JOptionPane.showMessageDialog(rootPane, "Kambario rezervacija sėkminga", "Kambario rezervacija", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Kambario rezervuoti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite rezervaciją", "Klaida", JOptionPane.ERROR_MESSAGE);
            } catch(ParseException ex) {
                Logger.getLogger(ManageReservationsForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class EditButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try{

                int reservationId = Integer.parseInt(textFieldReservationID.getText());
                int clientId = Integer.parseInt(textFieldClientID.getText());
                int roomNumber = Integer.parseInt(textFieldRoomNumber.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDate = dateFormat.format(dateIn.getDate());
                String finishDate = dateFormat.format(dateOut.getDate());
                String todayDate = dateFormat.format(new Date());

                Date start = dateFormat.parse(startDate);
                Date finish = dateFormat.parse(finishDate);
                Date today = dateFormat.parse(todayDate);

                if(!(start.after(today) || start.equals(today))){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite šiandienos arba būsimą rezervacijos pradžios datą", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
                else if(!(finish.after(start) || finish.equals(start))){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite būsimą rezervacijos pabaigos datą", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(reservation.editReservation(reservationId, clientId, roomNumber, startDate, finishDate)){

                        JOptionPane.showMessageDialog(rootPane, "Rezervacijos duomenys atnaujinti sėkmingai", "Redaguoti rezervaciją", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "ezervacijos atnaujinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite rezervaciją, kurią norite atnaujinti", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
            catch(NullPointerException ex){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite rezervacijos datą", "Klaida", JOptionPane.ERROR_MESSAGE);
            }catch(ParseException ex) {
                Logger.getLogger(ManageReservationsForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private class RemoveButtonCLicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                int reservationId = Integer.parseInt(textFieldReservationID.getText());

                if(reservation.removeReservation(reservationId)){

                    JOptionPane.showMessageDialog(rootPane, "Rezervacija pašalinta sėkmingai", "Pašalinti rezervaciją", JOptionPane.INFORMATION_MESSAGE);

                } else{

                    JOptionPane.showMessageDialog(rootPane, "Rezervacijos pašalinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite rezervaciją, kurį norite pašalinti", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RefreshButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            DefaultTableModel dm = (DefaultTableModel)tableReservations.getModel();
            dm.getDataVector().removeAllElements();
            dm.fireTableDataChanged();
            tableReservations.setModel(model);
            model.addRow(columns);
            reservation.fillReservationTableContents(tableReservations);
        }
    }

    private DefaultTableModel model = new DefaultTableModel(0, 5){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };


}
