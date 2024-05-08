package lk.ijse.dto;

import lk.ijse.db.Dbconnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {
    private String customerId;
    private String customerName;
    private String customerContactInformation;
    private String customerAddress;
    private String customerEmail;




}
