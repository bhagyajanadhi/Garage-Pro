package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.PaymentDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Payment {

    public static boolean save(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into payment values(?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentDto.getJobId());
        pstm.setString(2, paymentDto.getPaymentId());
        pstm.setString(3, paymentDto.getAmount());
        pstm.setString(4, String.valueOf(paymentDto.getDate()));
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
            String amount = resultSet.getString(3);
            LocalDate date = LocalDate.parse(resultSet.getString(4));

            PaymentDto payment = new PaymentDto( jobId, paymentId, amount, date);

            paymentList.add(payment);
        }
        return paymentList;
    }

    public static boolean update(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "update payment set jobId = ?,amount = ?,date = ? where paymentId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, paymentDto.getJobId());
        pstm.setString(4, paymentDto.getPaymentId());
        pstm.setString(2, paymentDto.getAmount());
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
}
