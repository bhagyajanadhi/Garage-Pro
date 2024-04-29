package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    public boolean adminCheck(String username, String password) {
        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
            pstm.setString(1, username);
            pstm.setString(2, password);

            ResultSet resultSet = pstm.executeQuery();
            return resultSet.next();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkDuplicateUsername(String username) {
        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM admin WHERE username=?");
            pstm.setString(1, username);

            ResultSet resultSet = pstm.executeQuery();
            return resultSet.next(); // If resultSet.next() returns true, it means a record with the given username exists

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean signUp(String username, String email, String password) {
        try {
            if (checkDuplicateUsername(username)) {
                return false; // Username already exists
            }

            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO admin (username, email, password) VALUES (?, ?, ?)");
            pstm.setString(1, username);
            pstm.setString(2, email);
            pstm.setString(3, password);

            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0; // If affectedRows > 0, insertion was successful

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
