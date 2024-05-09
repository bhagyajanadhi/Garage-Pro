package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.JobDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Job {


    public static boolean save(JobDto jobDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into job values(?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, jobDto.getJobId());
        pstm.setString(2, jobDto.getEmployeeId());
        pstm.setString(3, jobDto.getDescription());
        pstm.setString(4, String.valueOf(jobDto.getDate()));
        pstm.setString(5, jobDto.getVehicleId());
        return pstm.executeUpdate() > 0;
    }

    public static List<JobDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM job";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<JobDto> jobList = new ArrayList<>();
        while (resultSet.next()) {
            String jobId = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            String description = resultSet.getString(3);
            LocalDate date = LocalDate.parse(resultSet.getString(4));
            String vehicleId = resultSet.getString(5);

            JobDto job = new JobDto(jobId, employeeId, description, date,vehicleId);
            jobList.add(job);
        }
        return jobList;
    }

    public static boolean update(JobDto jobDto) throws SQLException, ClassNotFoundException {
        String sql = "update job set description = ?, date = ?, vehicleId = ?,employeeId =? where jobId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(5,jobDto.getJobId());
        pstm.setString(1,jobDto.getEmployeeId());
        pstm.setString(2,jobDto.getDescription());
        pstm.setString(3,String.valueOf(jobDto.getDate()));
        pstm.setString(4,jobDto.getVehicleId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String jobId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM job WHERE jobId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,jobId);

        return pstm.executeUpdate() > 0;

    }

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
}
