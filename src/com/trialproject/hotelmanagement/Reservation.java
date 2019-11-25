package com.trialproject.hotelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class Reservation extends JFrame {

    private MY_CONNECTION my_connection = new MY_CONNECTION();
    private Room room = new Room();

    Reservation() throws HeadlessException {
        //2 Foreign keys
        //ALTER TABLE reservations ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients(id) on DELETE CASCADE
        //ALTER TABLE reservations ADD CONSTRAINT fk_room_number FOREIGN KEY (room_number) REFERENCES rooms(room_number) on DELETE CASCADE

        //one more foreign key
        //ALTER TABLE rooms ADD CONSTRAINT fk_types_type FOREIGN KEY (room_type) REFERENCES types(type) on DELETE CASCADE



    }

    boolean addReservation(int client_id, int roomNumber, String dateIn, String dateOut){

        PreparedStatement st;
        String addQuery = "INSERT INTO `reservations`(`client_id`, `room_number`, `date_in`, `date_out`) VALUES (?,?,?,?)";

        try {

            st = my_connection.createConnection().prepareStatement(addQuery);

            st.setInt(1, client_id);
            st.setInt(2, roomNumber);
            st.setString(3, dateIn);
            st.setString(4, dateOut);

            if (room.isRoomReserved(roomNumber).equals("Ne")){
                if (st.executeUpdate() > 0){
                    room.setRoomReserved("Taip", roomNumber);
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Šis kambarys jau rezervuotas", "Klaida", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    boolean editReservation(int reservationId, int clientId, int roomNumber, String dateIn, String dateOut){

        PreparedStatement st;
        String editQuery = "UPDATE `reservations` SET `client_id`=?,`room_number`=?,`date_in`=?,`date_out`=? WHERE `id`=?";

        try {

            st = my_connection.createConnection().prepareStatement(editQuery);

            st.setInt(1, clientId);
            st.setInt(2, roomNumber);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            st.setInt(5, reservationId);

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    boolean removeReservation(int reservationId){

        PreparedStatement st;
        String removeQuery = "DELETE FROM `reservations` WHERE `id`=?";

        try {

            st = my_connection.createConnection().prepareStatement(removeQuery);

            st.setInt(1, reservationId);

            int roomNumber = getRoomNumberFromReservation(reservationId);

            if (st.executeUpdate() > 0){
                room.setRoomReserved("Ne", roomNumber);
                return true;
            }
            else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    void fillReservationTableContents(JTable table) {

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT * FROM `reservations`";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            rs = ps.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            Object[] row;

            while (rs.next()) {

                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);

                tableModel.addRow(row);
            }

        } catch (
                SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getRoomNumberFromReservation(int reservationId){

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT `room_number` FROM `reservations` WHERE `id`=?";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            ps.setInt(1, reservationId);

            rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
            else {
                return 0;
            }

        } catch (
                SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }
}
