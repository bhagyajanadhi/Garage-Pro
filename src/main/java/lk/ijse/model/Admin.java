package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.AdminDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
        public static AdminDto getAdmin(String email) throws SQLException {
            Connection connection = Dbconnection.getInstance().getConnection();
            String sql = "SELECT email, " +
                    "CONVERT(AES_DECRYPT(password, 'Ijse@123') USING utf8) AS decrypted_password, " +
                    "user_name, type " +
                    "FROM Admin " +
                    "WHERE email=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, email);

            ResultSet rst = pstm.executeQuery();
            AdminDto adminDto = new AdminDto();
            if (rst.next()) {
                adminDto.setEmail(rst.getString(1));
                adminDto.setPassword(rst.getString(2));
                adminDto.setUsername(rst.getString(3));
                return adminDto;
            } else {
                return null;
            }
        }
}
