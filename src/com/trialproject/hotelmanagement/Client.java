package com.trialproject.hotelmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client{

    private MY_CONNECTION my_connection = new MY_CONNECTION();

    boolean addClient(String fname, String lname, String email, String phone){

        PreparedStatement st;
        String addQuery = "INSERT INTO `clients`(`first_name`, `last_name`, `email`, `phone`) VALUES (?,?,?,?)";

        try {

            st = my_connection.createConnection().prepareStatement(addQuery);

            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, email);
            st.setString(4, phone);

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    boolean editClient(int id, String fname, String lname, String email, String phone){

        PreparedStatement st;
        String editQuery = "UPDATE `clients` SET `first_name`=?,`last_name`=?,`email`=?,`phone`=? WHERE `id`=?";

        try {

            st = my_connection.createConnection().prepareStatement(editQuery);

            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setInt(5, id);

            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    boolean removeClient(int id){

        PreparedStatement st;
        String removeQuery = "DELETE FROM `clients` WHERE `id`=?";

        try {

            st = my_connection.createConnection().prepareStatement(removeQuery);

            st.setInt(1, id);


            return (st.executeUpdate() > 0);

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    void fillClientTableContents(JTable table){

        PreparedStatement ps;
        ResultSet rs;
        String selectQurey = "SELECT * FROM `clients`";

        try {

            ps = my_connection.createConnection().prepareStatement(selectQurey);

            rs = ps.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            Object[] row;

            while(rs.next()){

                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);

                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
