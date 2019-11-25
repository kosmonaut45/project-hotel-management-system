package com.trialproject.hotelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllRoomTypeForm extends JFrame{
    private JPanel mainPanel;
    private JTable tableRoomTypes;
    private String[] columns = {"Kambario tipas", "Aprašymas", "Kaina"};

    private Room room = new Room();

    AllRoomTypeForm() {
        setContentPane(mainPanel);

        tableRoomTypes.setModel(model);
        tableRoomTypes.getColumnModel().getColumn(1).setMinWidth(400);
        tableRoomTypes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        model.addRow(columns);
        room.fillRoomsTypeTableContents(tableRoomTypes);
    }

    private DefaultTableModel model = new DefaultTableModel(0, 3){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
}
