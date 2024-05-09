package lk.ijse.model;

import lk.ijse.db.Dbconnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.CustomerDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    public static List<BookingDto> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM booking";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BookingDto> bookingList = new ArrayList<>();
        while (resultSet.next()) {
            String bookingId= resultSet.getString(1);
            String customerId = resultSet.getString(2);
            String vehicleId = resultSet.getString(3);
            LocalDate date = LocalDate.parse(resultSet.getString(4));

            BookingDto booking = new BookingDto(bookingId,customerId, vehicleId, date);
            bookingList.add(booking);
        }
        return bookingList;
    }

    public static boolean delete(String bookigId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM booking WHERE bookingId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,bookigId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        String sql = "insert into booking values(?,?,?,?)";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,bookingDto.getBookingId());
        pstm.setObject(2,bookingDto.getCustomerId());
        pstm.setObject(3,bookingDto.getVehicleId());
        pstm.setObject(4,bookingDto.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        String sql = "update booking set vehicleId = ?,customerId = ?,date = ? where bookingId =?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(4,bookingDto.getBookingId());
        pstm.setObject(2,bookingDto.getCustomerId());
        pstm.setObject(1,bookingDto.getVehicleId());
        pstm.setObject(3,bookingDto.getDate());

        return pstm.executeUpdate() > 0;
    }
}
