package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.CustomerDto;
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
            String skill = resultSet.getString(4);
            String contactInformation = resultSet.getString(5);

            EmployeeDto employee = new EmployeeDto(employeeId, salary, name, skill, contactInformation);
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
       pstm.setString(4, employeedto.getSkill());
       pstm.setObject(5, employeedto.getContactInformation());
        return pstm.executeUpdate() > 0;

    }

    public static boolean update(EmployeeDto employeedto) throws SQLException, ClassNotFoundException {
        String sql = "update employee set salary = ?,name = ?,skill = ? ,contactInformation =? where employeeId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(5,employeedto.getEmployeeId());
        pstm.setObject(1,employeedto.getSalary());
        pstm.setObject(2,employeedto.getName());
        pstm.setObject(3,employeedto.getSkill());
        pstm.setObject(4,employeedto.getContactInformation());
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

    public static EmployeeDto searchById(String contactInformation) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE  name=?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,contactInformation);
        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto employeeDto = null;




        if (resultSet.next()) {
            String inventoryId = resultSet.getString(1);
            double salary = Double.parseDouble(resultSet.getString(2));
            String name = resultSet.getString(3);
            String skill = resultSet.getString(4);
            String contact = resultSet.getString(5);


            employeeDto  = new EmployeeDto( inventoryId, salary,name,skill,contact);
        }
        return employeeDto;

    }
}
