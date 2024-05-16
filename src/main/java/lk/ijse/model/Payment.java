package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.PaymentDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Payment {

    public static boolean save(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into payment values(?,?,?,?,?,?)";
        System.out.println(paymentDto);
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentDto.getJobId());
        pstm.setString(2, paymentDto.getPaymentId());
        pstm.setDouble(3, paymentDto.getAmount());
        pstm.setDate(4, Date.valueOf(paymentDto.getDate()));
        pstm.setDouble(5,paymentDto.getItemAmount());
        pstm.setDouble(6,paymentDto.getTotal());
        return pstm.executeUpdate() > 0;
    }

    public static List<PaymentDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "select * from payment";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<PaymentDto> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String jobId = resultSet.getString(1);
            String paymentId = resultSet.getString(2);
            Double amount = Double.valueOf(resultSet.getString(3));
            LocalDate date = LocalDate.parse(resultSet.getString(4));
            Double itemAmount = Double.valueOf(resultSet.getString(5));
            Double total= Double.valueOf(resultSet.getString(6));
            PaymentDto payment = new PaymentDto( jobId, paymentId, amount, date,itemAmount,total);

            paymentList.add(payment);
        }
        return paymentList;
    }

    public static boolean update(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "update payment set jobId = ?,amount = ?,date = ? where paymentId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentDto.getJobId());
        pstm.setString(4, paymentDto.getPaymentId());
        pstm.setDouble(2, paymentDto.getAmount());
        pstm.setString(3, String.valueOf(paymentDto.getDate()));
        return pstm.executeUpdate() > 0 ;



    }

    public static boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM payment WHERE paymentId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,paymentId);

        return pstm.executeUpdate() > 0;
    }

    public static PaymentDto searchById(String paymentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment WHERE  paymentId=?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, paymentId);
        ResultSet resultSet = pstm.executeQuery();

        PaymentDto paymentDto = null;


        if (resultSet.next()) {
            String jobId = resultSet.getString(1);
            paymentId = resultSet.getString(2);
            Double amount = Double.valueOf(resultSet.getString(3));
            LocalDate date = LocalDate.parse(resultSet.getString(4));
            double itemAmount = Double.valueOf(resultSet.getString(5));
            double total = resultSet.getDouble(6);


            paymentDto = new PaymentDto(jobId,paymentId,amount,date,itemAmount,total);
        }
        return paymentDto;
    }
}
