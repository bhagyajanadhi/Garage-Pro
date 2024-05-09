package lk.ijse.model;


import lk.ijse.db.Dbconnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.JobDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Customer {




    public static boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException {

        String sql = "insert into customer values(?,?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


            pstm.setObject(1,dto.getCustomerId());
            pstm.setObject(2,dto.getCustomerName());
            pstm.setObject(3,dto.getCustomerContactInformation());
            pstm.setObject(4,dto.getCustomerAddress());
            pstm.setObject(5,dto.getCustomerEmail());

            return pstm.executeUpdate() > 0;
    }


    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT customerId FROM customer";

        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }


    public static List<CustomerDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<CustomerDto> customerList = new ArrayList<>();
        while (resultSet.next()) {
            String customerId = resultSet.getString(1);
            String customerName = resultSet.getString(2);
            String customerContactInformation = resultSet.getString(3);
            String customerAddress = resultSet.getString(4);
            String customerEmail = resultSet.getString(5);

            CustomerDto customer = new CustomerDto(customerId, customerName, customerContactInformation, customerAddress,customerEmail);
            customerList.add(customer);
        }
        return customerList;
    }

    public static boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,customerId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql = "update customer set customerName = ?,customerContactInfromation = ?,customerAddress = ?,customerEmail = ? where customerId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(5,dto.getCustomerId());
        pstm.setObject(1,dto.getCustomerName());
        pstm.setObject(2,dto.getCustomerContactInformation());
        pstm.setObject(3,dto.getCustomerAddress());
        pstm.setObject(4,dto.getCustomerEmail());
        return pstm.executeUpdate() > 0;
    }


    public static CustomerDto searchById(String customerContactInformation) throws SQLException, ClassNotFoundException {

            String sql = "SELECT * FROM customer WHERE  customerContactInfromation=?";

            PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                    .prepareStatement(sql);

            pstm.setObject(1,customerContactInformation);
            ResultSet resultSet = pstm.executeQuery();

            CustomerDto customerDto = null;




            if (resultSet.next()) {
                String customerId = resultSet.getString(1);
                String customerName = resultSet.getString(2);
                String contact = resultSet.getString(3);
                String customerAddress = resultSet.getString(4);
                String customerEmail = resultSet.getString(5);


                customerDto  = new CustomerDto( customerId, customerName,contact, customerAddress,customerEmail);
            }
            return customerDto;
        }

}




