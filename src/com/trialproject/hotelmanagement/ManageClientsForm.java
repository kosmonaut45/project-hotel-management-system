package com.trialproject.hotelmanagement;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class ManageClientsForm extends JFrame {

    private JPanel mainPanel;
    private JTextField textFieldID;
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldEmail;
    private JTextField textFieldPhoneNumber;
    private JTable tableClients;
    private JButton buttonAdd;
    private JButton buttonEdit;
    private JButton buttonRemove;
    private JLabel labelID;
    private JLabel labelName;
    private JLabel labelSurname;
    private JLabel labelEmail;
    private JLabel labelPhoneNumber;
    private JLabel labelMangeClients;
    private JButton buttonClearFields;
    private JButton buttonRefresh;
    private JPanel panelClientTextBackground;
    private JPanel panelBackground;
    private JLabel labelCompulsaryFields;
    private String[] columns = {"ID", "Vardas", "Pavardė", "El. paštas", "Tel. nr."};

    private Client client = new Client();

    ManageClientsForm() {

        setContentPane(mainPanel);

        buttonAdd.addActionListener(new AddButtonClicked());
        buttonClearFields.addActionListener(new ClearButtonClicked());
        buttonEdit.addActionListener(new EditButtonClicked());
        buttonRemove.addActionListener(new RemoveButtonClicked());
        buttonRefresh.addActionListener(new RefreshButtonClicked());

        tableClients.setModel(model);
        model.addRow(columns);
        client.fillClientTableContents(tableClients);

        tableClients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel model = (DefaultTableModel) tableClients.getModel();

                int rowIndex = tableClients.getSelectedRow();

                try{
                    if(rowIndex != 0){
                        textFieldID.setText(model.getValueAt(rowIndex, 0).toString());
                        textFieldName.setText(model.getValueAt(rowIndex, 1).toString());
                        textFieldSurname.setText(model.getValueAt(rowIndex, 2).toString());
                        textFieldEmail.setText(model.getValueAt(rowIndex, 3).toString());
                        textFieldPhoneNumber.setText(model.getValueAt(rowIndex, 4).toString());
                    }
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    //JOptionPane.showMessageDialog(rootPane, "Pasirinkite vartotoją", "Klaida", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private class AddButtonClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String fname = textFieldName.getText();
            String lname = textFieldSurname.getText();
            String email = textFieldEmail.getText();
            String phone = textFieldPhoneNumber.getText();

            if(fname.trim().equals("") || lname.trim().equals("") || phone.trim().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Privalomi laukeliai", "Pridėti klientą", JOptionPane.WARNING_MESSAGE);
            } else {
                if(client.addClient(fname, lname, email, phone)){

                    JOptionPane.showMessageDialog(rootPane, "Naujas klientas pridėtas sėkmingai", "Pridėti klientą", JOptionPane.INFORMATION_MESSAGE);

                } else{

                    JOptionPane.showMessageDialog(rootPane, "Kliento pridėti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class EditButtonClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            int id;
            String fname = textFieldName.getText();
            String lname = textFieldSurname.getText();
            String email = textFieldEmail.getText();
            String phone = textFieldPhoneNumber.getText();

            if(fname.trim().equals("") || lname.trim().equals("") || phone.trim().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite klientą", "Redaguoti kliento duomenis", JOptionPane.WARNING_MESSAGE);
            } else {

                try{
                    id = Integer.parseInt(textFieldID.getText());

                    if(client.editClient(id, fname, lname, email, phone)){

                        JOptionPane.showMessageDialog(rootPane, "Kliento duomenys atnaujinti sėkmingai", "Redaguoti kliento duomenis", JOptionPane.INFORMATION_MESSAGE);

                    } else{

                        JOptionPane.showMessageDialog(rootPane, "Kliento duomenų atnaujinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite vartotoją, kurį norite atnaujinti", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemoveButtonClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                int id = Integer.parseInt(textFieldID.getText());

                if(client.removeClient(id)){

                    JOptionPane.showMessageDialog(rootPane, "Klientas pašalintas sėkmingai", "Pašalinti klientą", JOptionPane.INFORMATION_MESSAGE);

                } else{

                    JOptionPane.showMessageDialog(rootPane, "Kliento pašalinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, "Pasirinkite vartotoją, kurį norite pašalinti", "Klaida", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private DefaultTableModel model = new DefaultTableModel(0,5){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private class ClearButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            textFieldID.setText("");
            textFieldName.setText("");
            textFieldSurname.setText("");
            textFieldEmail.setText("");
            textFieldPhoneNumber.setText("");

        }

    }

    private class RefreshButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            DefaultTableModel dm = (DefaultTableModel) tableClients.getModel();
            dm.getDataVector().removeAllElements();
            dm.fireTableDataChanged();
            tableClients.setModel(model);
            model.addRow(columns);
            client.fillClientTableContents(tableClients);

        }
    }
}
