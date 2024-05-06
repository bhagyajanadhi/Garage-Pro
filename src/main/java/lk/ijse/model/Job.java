package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.JobDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Job {
    public static boolean delete(String jobId) {
    }

    public static boolean save(JobDto jobDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into job values(?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, jobDto.getJobId());
        pstm.setString(2, jobDto.getEmployeeId());
        pstm.setDouble(3, jobDto.getDescription());
        pstm.setString(4, jobDto.getDate());
        pstm.setString(5, jobDto.getVehicleId());
        return pstm.executeUpdate() > 0;
    }

    public static List<JobDto> getAll() {
    }
}
