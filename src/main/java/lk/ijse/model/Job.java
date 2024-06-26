package lk.ijse.model;

import javafx.collections.ObservableList;
import lk.ijse.db.Dbconnection;
import lk.ijse.dto.*;

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

    public static List<dashJobDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT jobId, employeeId, date FROM job;";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<dashJobDto> joblist = new ArrayList<>();
        while (resultSet.next()){
            String jobId = resultSet.getString(1);
            String emloyeeId = resultSet.getString(2);
            LocalDate date = resultSet.getDate(3).toLocalDate();

            dashJobDto job = new dashJobDto(jobId, emloyeeId, date);
            joblist.add(job);
        }
        return joblist;
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
            System.out.println(dto);
            System.out.println(jobId);
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into job_inventory values(?,?,?,?,?)");
            pstm.setObject(1, jobId);
            pstm.setObject(2, dto.getInventoryId());
            pstm.setObject(3, dto.getQty());
            pstm.setObject(4, dto.getUnitePrice());
            pstm.setObject(5, dto.getTotalPrice());
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


    public static JobDto searchById(String jobId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM job WHERE jobId =?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);


        pstm.setObject(1,jobId);
        ResultSet resultSet = pstm.executeQuery();

        JobDto jobDto = null;

        if (resultSet.next()) {

            jobId = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String description = resultSet.getString(3);
            LocalDate date = LocalDate.parse(resultSet.getString(4));
            String vehicleId = resultSet.getString(5);
            Double total = resultSet.getDouble(6);


            jobDto  = new JobDto(jobId,employeeId,description,date,vehicleId,total);

        }
        return jobDto;
    }
}