package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    public static boolean delete(String vehicleId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM vehicle WHERE vehicleId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,vehicleId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into vehicle values(?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,vehicleDto.getVehicleId());
        pstm.setObject(3,vehicleDto.getVehicleModel());
        pstm.setObject(2,vehicleDto.getVehicleLicensePlate());
        pstm.setObject(4,vehicleDto.getCus_id());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        String sql = "update vehicle set vehicleModel = ?,vehicleLicense = ?,cus_id = ? where vehicleId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,vehicleDto.getVehicleId());
        pstm.setObject(3,vehicleDto.getVehicleModel());
        pstm.setObject(2,vehicleDto.getVehicleLicensePlate());
        pstm.setObject(4,vehicleDto.getCus_id());

        return pstm.executeUpdate() > 0;
    }
    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT vehicleId FROM vehicle";

        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static List<VehicleDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM vehicle";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<VehicleDto> vehicleList = new ArrayList<>();
        while (resultSet.next()) {
            String vehicleId = resultSet.getString(1);
            String vehicleModel = resultSet.getString(3);
            String vehicleLicense = resultSet.getString(2);
            String cus_id = resultSet.getString(4);

            VehicleDto vehicle = new VehicleDto(vehicleId, vehicleModel, vehicleLicense,cus_id);
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public static VehicleDto searchById(String vehicleLicensePlate) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM vehicle WHERE vehicleLicensePlate =?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,vehicleLicensePlate);
        ResultSet resultSet = pstm.executeQuery();

        VehicleDto vehicleDto = null;




        if (resultSet.next()) {
            String vehicleId = resultSet.getString(1);
            String vehicleModel = resultSet.getString(2);
            String plate = resultSet.getString(3);
            String cus_Id = resultSet.getString(4);


            vehicleDto  = new VehicleDto( vehicleId,vehicleModel,plate,cus_Id);
        }
        return vehicleDto;
    }

}
