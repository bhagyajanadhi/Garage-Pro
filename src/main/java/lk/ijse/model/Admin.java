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
            PreparedStatement pstm = connection.prepareStatement("select * from admin where username=? && password=?");
            pstm.setObject(1,username);
            pstm.setObject(2, password);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    }
