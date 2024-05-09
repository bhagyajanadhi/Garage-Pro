package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.InventoryDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static boolean delete(String inventoryId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM inventory WHERE inventoryId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, inventoryId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into inventory values(?,?,?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1, inventoryDto.getInventoryId());
        pstm.setObject(2, inventoryDto.getDescription());
        pstm.setObject(3, inventoryDto.getSupplierId());
        pstm.setObject(4, inventoryDto.getPartName());
        pstm.setObject(5, inventoryDto.getStockLevel());
        pstm.setObject(6, inventoryDto.getUnitePrice());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "update inventory set description = ?,supplierId = ?,partName = ?,stockLevel = ?,unitePrice=? where inventoryId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(5, inventoryDto.getInventoryId());
        pstm.setObject(1, inventoryDto.getDescription());
        pstm.setObject(2, inventoryDto.getSupplierId());
        pstm.setObject(3, inventoryDto.getPartName());
        pstm.setObject(4, inventoryDto.getStockLevel());
        pstm.setObject(5, inventoryDto.getUnitePrice());
        return pstm.executeUpdate() > 0;
    }

    public static InventoryDto searchById(String partName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM inventory WHERE  partName=?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, partName);
        ResultSet resultSet = pstm.executeQuery();

        InventoryDto inventoryDto = null;


        if (resultSet.next()) {
            String inventoryId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String supplierId = resultSet.getString(3);
            String PrtName = resultSet.getString(4);
            int stockLevel = Integer.parseInt(resultSet.getString(5));
            double unitePrice = resultSet.getDouble(6);


            inventoryDto = new InventoryDto(inventoryId, description, supplierId, PrtName, stockLevel, unitePrice);
        }
        return inventoryDto;
    }

    public static List<InventoryDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM inventory";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<InventoryDto> inventoryList = new ArrayList<>();
        while (resultSet.next()) {
            String inventoryId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String supplierId = resultSet.getString(3);
            String partName = resultSet.getString(4);
            int stockLevel = Integer.parseInt(resultSet.getString(5));
            double unitPrice = Double.parseDouble(resultSet.getString(6));


            InventoryDto inventoryDto = new InventoryDto(inventoryId, description, supplierId, partName, stockLevel, unitPrice);
            inventoryList.add(inventoryDto);
        }

        return inventoryList;
    }
}
