package com.trialproject.hotelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ManageRoomsForm extends JFrame {
    private JLabel labelMangeClients;
    private JLabel labelRoomNumber;
    private JTextField textFieldNumber;
    private JLabel labelName;
    private JLabel labelRoomPhone;
    private JTextField textFieldPhone;
    private JTable tableRooms;
    private JButton buttonAdd;
    private JButton buttonRemove;
    private JButton buttonEdit;
    private JButton buttonClearFields;
    private JButton buttonRefresh;
    private JPanel mainPanel;
    private JComboBox comboBoxRoomType;
    private JButton buttonShowTypes;
    private JLabel labelReserved;
    private JRadioButton radioButtonYes;
    private JRadioButton radioButtonNo;
    private String[] columns = {"Kambario numberis", "Tipas", "Telefono numeris", "Rezervuotas"};

    private Room room = new Room();

    ManageRoomsForm() {
        setContentPane(mainPanel);

        buttonClearFields.addActionListener(new ClearButtonClicked());
        buttonAdd.addActionListener(new AddButtonClicked());
        buttonEdit.addActionListener(new EditButtonClicked());
        buttonRemove.addActionListener(new RemoveButtonCLicked());
        buttonRefresh.addActionListener(new RefreshButtonClicked());
        buttonShowTypes.addActionListener(new ShowTypesButtonClicked());

        room.fillRoomsTypeComboBox(comboBoxRoomType);

        tableRooms.setModel(model);
        model.addRow(columns);
        room.fillRoomsTableContents(tableRooms);

        tableRooms.setRowHeight(25);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButtonNo);
        bg.add(radioButtonYes);

        tableRooms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel model = (DefaultTableModel) tableRooms.getModel();

                int rowIndex = tableRooms.getSelectedRow();

                try{
                    if(rowIndex != 0){
                        textFieldNumber.setText(model.getValueAt(rowIndex, 0).toString());
                        comboBoxRoomType.setSelectedItem(model.getValueAt(rowIndex, 1));
                        textFieldPhone.setText(model.getValueAt(rowIndex, 2).toString());

                        String isReserved = model.getValueAt(rowIndex, 3).toString();

                        if(isReserved.equals("Taip")){
                            radioButtonYes.setSelected(true);
                        }
                        else if(isReserved.equals("Ne")){
                            radioButtonNo.setSelected(true);
                        }
                    }
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    //JOptionPane.showMessageDialog(rootPane, ex.getMessage() + "Pasirinkite kambarį", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private class ClearButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            textFieldNumber.setText("");
            comboBoxRoomType.setSelectedIndex(0);
            textFieldPhone.setText("");
            radioButtonNo.setSelected(true);
        }
    }

    private class AddButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            try{

                int roomNumber = Integer.parseInt(textFieldNumber.getText());
                String roomType = Objects.requireNonNull(comboBoxRoomType.getSelectedItem()).toString();
                String phone = textFieldPhone.getText();

                if(room.addRoom(roomNumber, roomType, phone)){

                    JOptionPane.showMessageDialog(rootPane, "Kambarys užsakytas sėkmingai", "Kambario užsakymas", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Kambario užsakyti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                }

            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage() + "Pasirinkite kambario numerį", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class EditButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            int roomNumber;
            String type = Objects.requireNonNull(comboBoxRoomType.getSelectedItem()).toString();
            String phone = textFieldPhone.getText();
            String isReserved;

            if(radioButtonYes.isSelected()){
                isReserved = "Taip";
            }
            else {
                isReserved = "Ne";
            }

            if(phone.trim().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Privalomi laukeliai", "Redaguoti rezervaciją", JOptionPane.WARNING_MESSAGE);
            } else {

                try{

                    roomNumber = Integer.parseInt(textFieldNumber.getText());

                    if(room.editRoom(roomNumber, type, phone, isReserved)){

                        JOptionPane.showMessageDialog(rootPane, "Kambario informacija atnaujinta sėkmingai", "Redaguoti rezervaciją", JOptionPane.INFORMATION_MESSAGE);

                    } else{

                        JOptionPane.showMessageDialog(rootPane, "Kambario informacijos atnaujinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(rootPane, "Pasirinkite kambarį, kurio informaciją norite atnaujinti", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }

    private class RemoveButtonCLicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                int roomNumber = Integer.parseInt(textFieldNumber.getText());

                if(room.removeRoom(roomNumber)){

                    JOptionPane.showMessageDialog(rootPane, "Kambarys pašalintas sėkmingai", "Pašalinti Kambarį", JOptionPane.INFORMATION_MESSAGE);

                } else{

                    JOptionPane.showMessageDialog(rootPane, "Kambario pašalinti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage() + "Pasirinkite kambarį, kurį norite pašalinti", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RefreshButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            DefaultTableModel dm = (DefaultTableModel)tableRooms.getModel();
            dm.getDataVector().removeAllElements();
            dm.fireTableDataChanged();
            tableRooms.setModel(model);
            model.addRow(columns);
            room.fillRoomsTableContents(tableRooms);
        }
    }

    private class ShowTypesButtonClicked implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            AllRoomTypeForm roomTypeForm = new AllRoomTypeForm();
            roomTypeForm.pack();
            roomTypeForm.setVisible(true);
            roomTypeForm.setSize(800, 200);
            roomTypeForm.setLocationRelativeTo(null);
            //roomTypeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
    }

    private DefaultTableModel model = new DefaultTableModel(0, 4){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
}
