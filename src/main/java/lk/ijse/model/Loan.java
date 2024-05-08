package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.LoanDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    public static List<LoanDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM loan";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<LoanDto> loanList = new ArrayList<>();
        while (resultSet.next()) {
            String LoanId = resultSet.getString(1);
            String amount = resultSet.getString(2);
            String supplierId = resultSet.getString(3);
            LocalDate duedate = LocalDate.parse(resultSet.getString(4));
            String paymentstatus = resultSet.getString(5);

            LoanDto loan = new LoanDto(LoanId, amount,supplierId,duedate,paymentstatus);
            loanList.add(loan);
        }
        return loanList;
    }

    public static boolean delete(String loanId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM loan WHERE LoanId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,loanId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(LoanDto loanDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into loan values(?,?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,loanDto.getLoanId());
        pstm.setObject(2,loanDto.getAmount());
        pstm.setObject(3,loanDto.getSupplierId());
        pstm.setObject(4,loanDto.getDuedate());
        pstm.setObject(5,loanDto.getPaymentstatus());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(LoanDto loanDto) throws SQLException, ClassNotFoundException {
        String sql = "update loan set amount = ?,supplierId = ?,duedates = ?,paymentstatus = ? where LoanId =?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,loanDto.getLoanId());
        pstm.setObject(2,loanDto.getAmount());
        pstm.setObject(3,loanDto.getSupplierId());
        pstm.setObject(4,loanDto.getDuedate());
        pstm.setObject(5,loanDto.getPaymentstatus());

        return pstm.executeUpdate() > 0;
    }
}
