package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee {


    public static List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from employee";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<EmployeeDto> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String jobId = resultSet.getString(2);
            double salary = resultSet.getDouble(3);
            String name = resultSet.getString(4);
            String skills = resultSet.getString(5);
            String contactInformations = resultSet.getString(6);

            EmployeeDto employee = new EmployeeDto(employeeId, jobId, salary, name, skills, contactInformations);
            employeeList.add(employee);
        }
    return employeeList;
    }

    public static boolean save(EmployeeDto employeedto) throws SQLException, ClassNotFoundException {
        String sql = "insert into employee values(?,?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
       pstm.setString(1, employeedto.getEmployeeId());
       pstm.setString(2, employeedto.getJobId());
       pstm.setDouble(3, employeedto.getSalary());
       pstm.setString(4, employeedto.getName());
       pstm.setString(5, employeedto.getSkills());
       pstm.setObject(6, employeedto.getContactInfromation());
        return pstm.executeUpdate() > 0;

    }

    public static boolean update(EmployeeDto employeedto) throws SQLException, ClassNotFoundException {
        String sql = "update employee set jobId = ?,salary = ?,name = ?,skills = ? ,contactInformation =? where employeeId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employeedto.getEmployeeId());
        pstm.setObject(2,employeedto.getJobId());
        pstm.setObject(3,employeedto.getSalary());
        pstm.setObject(4,employeedto.getName());
        pstm.setObject(5,employeedto.getSkills());
        pstm.setObject(6,employeedto.getContactInfromation());
        return pstm.executeUpdate() > 0;

    }

    public int saveEmpolyee(EmployeeDto employeeDto) {
        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm =connection.prepareStatement("insert into customer values(?,?,?,?,?,?)");
            pstm.setObject(1,employeeDto.getEmployeeId());
            pstm.setObject(2,employeeDto.getJobId());
            pstm.setObject(3,employeeDto.getSalary());
            pstm.setObject(4,employeeDto.getName());
            pstm.setObject(5,employeeDto.getSkills());
            pstm.setObject(6,employeeDto.getContactInfromation());


            return pstm.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
