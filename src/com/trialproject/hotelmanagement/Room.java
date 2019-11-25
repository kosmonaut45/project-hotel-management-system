package com.trialproject.hotelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Room {

    private MY_CONNECTION my_connection = new MY_CONNECTION();

    void fillRoomsTypeTableContents(JTable table) {

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT * FROM `types`";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            rs = ps.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            Object[] row;

            while (rs.next()) {

                row = new Object[3];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                tableModel.addRow(row);
            }

        } catch (
                SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void fillRoomsTableContents(JTable table) {

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT * FROM `rooms`";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            rs = ps.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            Object[] row;

            while (rs.next()) {

                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                tableModel.addRow(row);
            }

        } catch (
                SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void fillRoomsTypeComboBox(JComboBox comboBox) {

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT * FROM `types`";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            rs = ps.executeQuery();

            while (rs.next()) {

                comboBox.addItem(rs.getString(1));
            }

        } catch (
                SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean addRoom(int number, String type, String phone){

        PreparedStatement st;
        String addQuery = "INSERT INTO `rooms`(`room_number`, `room_type`, `phone`, `reserved`) VALUES (?,?,?,?)";

        try {

            st = my_connection.createConnection().prepareStatement(addQuery);

            st.setInt(1, number);
            st.setString(2, type);
            st.setString(3, phone);
            st.setString(4, "Ne");

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    boolean editRoom(int number, String type, String phone, String isReserved){

        PreparedStatement st;
        String editQuery = "UPDATE `rooms` SET`room_type`=?,`phone`=?,`reserved`=? WHERE `room_number`=?";

        try {

            st = my_connection.createConnection().prepareStatement(editQuery);

            st.setString(1, type);
            st.setString(2, phone);
            st.setString(3, isReserved);
            st.setInt(4, number);

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    boolean removeRoom(int roomNumber){

        PreparedStatement st;
        String removeQuery = "DELETE FROM `rooms` WHERE `room_number`=?";

        try {

            st = my_connection.createConnection().prepareStatement(removeQuery);

            st.setInt(1, roomNumber);


            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    boolean setRoomReserved(String isReserved, int number){

        PreparedStatement st;
        String editQuery = "UPDATE `rooms` SET`reserved`=? WHERE `room_number`=?";

        try {

            st = my_connection.createConnection().prepareStatement(editQuery);

            st.setString(1, isReserved);
            st.setInt(2, number);

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    String isRoomReserved(int number){

        PreparedStatement st;
        ResultSet rs;
        String checkQuery = "SELECT `reserved` FROM `rooms` WHERE `room_number`=?";

        try {

            st = my_connection.createConnection().prepareStatement(checkQuery);

            st.setInt(1, number);

            rs = st.executeQuery();

            if(rs.next()) {
                return rs.getString(1);
            }
            else{
                return "";
            }

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }
}
