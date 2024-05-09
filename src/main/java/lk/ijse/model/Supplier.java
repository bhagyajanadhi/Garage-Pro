package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        pstm.setObject(3, supplierDto.getName());
        pstm.setObject(2, supplierDto.getContactInformaton());
        pstm.setObject(4, supplierDto.getContactInformaton());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "update supplier set name = ?,contactInformation = ?,paymentTerms = ? where supplierId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(4, supplierDto.getSupplierId());
        pstm.setObject(1, supplierDto.getName());
        pstm.setObject(2, supplierDto.getContactInformaton());
        pstm.setObject(3, supplierDto.getContactInformaton());

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
            String contactInfromation = resultSet.getString(4);


            supplierDto = new SupplierDto(supplierId, supplierName, paymentTerm, contactInfromation);
        }
        return supplierDto;
    }

}

