package lk.ijse.model;

import javafx.collections.ObservableList;
import lk.ijse.db.Dbconnection;
import lk.ijse.dto.InventoryDto;
import lk.ijse.dto.JobDto;
import lk.ijse.dto.JobInventoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Job {


    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT jobId FROM job";

        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public boolean saveJob(String jobId, String employeeId, String description, LocalDate date, String vehicleId, ObservableList<JobInventoryDto> observableList, double fullTotal) throws SQLException {

        Connection connection = null;
        try {
            connection = Dbconnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            //String jobId, String employeeId, String description, LocalDate date, String vehicleId, double total
            boolean saveJob = save(new JobDto(jobId, employeeId, description, date, vehicleId, fullTotal));
            if (saveJob == true) {

                boolean saveJobDetails = JobDetailsSave(jobId, observableList);
                if (saveJobDetails == true) {
                    boolean b = updateInventoryQty(observableList);
                    if (b == true) {
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private boolean updateInventoryQty(ObservableList<JobInventoryDto> observableList) throws SQLException, ClassNotFoundException {
        for (JobInventoryDto dto : observableList) {
            Inventory inventory = new Inventory();
            InventoryDto inventoryDto = inventory.searchById(dto.getPartName());
            boolean b = inventory.update(new InventoryDto(inventoryDto.getInventoryId(),inventoryDto.getDescription(),inventoryDto.getSupplierId(),inventoryDto.getPartName(),inventoryDto.getQty()-dto.getQty(),inventoryDto.getUnitePrice()));
            if (!b) {
                return false;
            }
        }
        return true;
    }


    private boolean JobDetailsSave(String jobId, ObservableList<JobInventoryDto> observableList) throws SQLException, ClassNotFoundException {

        for (JobInventoryDto dto : observableList) {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into job_inventory values(?,?,?,?,?)");
            pstm.setObject(1, jobId);
            pstm.setObject(2, dto.getInventoryId());
            pstm.setObject(3, dto.getQty());
            pstm.setObject(4, dto.getUnitePrice());

            pstm.setObject(5, dto.getUnitePrice());
            boolean b = pstm.executeUpdate() > 0;
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean save(JobDto jobDto) throws SQLException, ClassNotFoundException {
        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("insert into job values(?,?,?,?,?,?)");
        pstm.setObject(1, jobDto.getJobId());
        pstm.setObject(2, jobDto.getEmployeeId());
        pstm.setObject(3, jobDto.getDescription());
        pstm.setObject(4, jobDto.getDate());
        pstm.setObject(5,jobDto.getVehicleId());
        pstm.setObject(6,jobDto.getTotal());
        return pstm.executeUpdate() > 0;
    }




}