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
            double salary = resultSet.getDouble(2);
            String name = resultSet.getString(3);
            String skills = resultSet.getString(4);
            String contactInformations = resultSet.getString(5);

            EmployeeDto employee = new EmployeeDto(employeeId, salary, name, skills, contactInformations);
            employeeList.add(employee);
        }
    return employeeList;
    }

    public static boolean save(EmployeeDto employeedto) throws SQLException, ClassNotFoundException {
        String sql = "insert into employee values(?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
       pstm.setString(1, employeedto.getEmployeeId());
       pstm.setDouble(2, employeedto.getSalary());
       pstm.setString(3, employeedto.getName());
       pstm.setString(4, employeedto.getSkills());
       pstm.setObject(5, employeedto.getContactInfromation());
        return pstm.executeUpdate() > 0;

    }

    public static boolean update(EmployeeDto employeedto) throws SQLException, ClassNotFoundException {
        String sql = "update employee set salary = ?,name = ?,skills = ? ,contactInformation =? where employeeId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,employeedto.getEmployeeId());
        pstm.setObject(2,employeedto.getSalary());
        pstm.setObject(3,employeedto.getName());
        pstm.setObject(4,employeedto.getSkills());
        pstm.setObject(5,employeedto.getContactInfromation());
        return pstm.executeUpdate() > 0;

    }


    public static boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,employeeId);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT employeeId FROM employee";

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