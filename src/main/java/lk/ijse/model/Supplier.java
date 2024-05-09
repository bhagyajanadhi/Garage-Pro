package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplierId FROM supplier";

        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static boolean delete(String supplierID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplierID);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into supplier values(?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1, supplierDto.getSupplierId());
        pstm.setObject(2, supplierDto.getName());
        pstm.setObject(4, supplierDto.getContactInformation());
        pstm.setObject(3, supplierDto.getPaymentTerm());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "update supplier set name = ?,contactInformation = ?,paymentTerms = ? where supplierId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(4, supplierDto.getSupplierId());
        pstm.setObject(1, supplierDto.getName());
        pstm.setObject(2, supplierDto.getContactInformation());
        pstm.setObject(3, supplierDto.getContactInformation());

        return pstm.executeUpdate() > 0;


    }

    public static SupplierDto searchById(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier WHERE name =?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();

        SupplierDto supplierDto = null;


        if (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String supplierName = resultSet.getString(2);
            String paymentTerm = resultSet.getString(3);
            String contactInformation = resultSet.getString(4);


            supplierDto = new SupplierDto(supplierId, supplierName, paymentTerm, contactInformation);
        }
        return supplierDto;
    }

    public static List<SupplierDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<SupplierDto> supplierList = new ArrayList<>();
        while (resultSet.next()) {
            String supplierId= resultSet.getString(1);
            String name = resultSet.getString(2);
            String paymentTerm = resultSet.getString(3);
            String contactInformation = resultSet.getString(4);

            SupplierDto supplierDto = new SupplierDto(supplierId,name,paymentTerm,contactInformation);
            supplierList.add(supplierDto);
        }
        return supplierList;
    }
}

