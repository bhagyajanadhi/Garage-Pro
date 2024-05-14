package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.InventoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        pstm.setObject(5, inventoryDto.getQty());
        pstm.setObject(6, inventoryDto.getUnitePrice());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "update inventory set description = ?,supplierId = ?,partName = ?,stockLevel = ?,unitPrice=? where inventoryId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(6, inventoryDto.getInventoryId());
        pstm.setObject(1, inventoryDto.getDescription());
        pstm.setObject(2, inventoryDto.getSupplierId());
        pstm.setObject(3, inventoryDto.getPartName());
        pstm.setObject(4, inventoryDto.getQty());
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


    public static ArrayList<InventoryDto> getAllInventory() {
        ArrayList<InventoryDto> allData = new ArrayList<InventoryDto>();

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM inventory");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                allData.add(
                        new InventoryDto(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getDouble(6)

                        )
                );
            }
            return  allData;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
