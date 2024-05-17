package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lk.ijse.db.Dbconnection;
import lk.ijse.dto.dashJobDto;
import lk.ijse.model.Inventory;
import lk.ijse.model.Job;
import lk.ijse.util.DateTimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardPaneForm {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private TableColumn<?, ?> ColDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colJobId;

    @FXML
    private TableView<dashJobDto> tblJobId;




@FXML
    private Label lblAuto;

    @FXML
    private Label lblEmployee;

    private List<dashJobDto> jobList = new ArrayList<>();
    public void initialize() throws SQLException, ClassNotFoundException {
        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        getAllJob();
        setDataToBarChart();


        try {
            // Count the number of employees and set the value to lblEmployee
            countEntities("SELECT COUNT(*) AS employees FROM employee", lblEmployee, "employees");

            // Count the number of vehicles and set the value to lblAuto
            countEntities("SELECT COUNT(*) AS vehicles FROM vehicle", lblAuto, "vehicles");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to handle it at a higher level if necessary
        }
    }

    private void setDataToBarChart() throws SQLException, ClassNotFoundException {
        ObservableList<XYChart.Series<String, Integer>> barChartData = Inventory.getDataToBarChart();

        barChart.setData(barChartData);
    }


    private void getAllJob() throws SQLException, ClassNotFoundException {
        jobList = Job.getAll();
        tblJobId.setItems(FXCollections.observableList(this.jobList));



    }

    private void countEntities(String sql, Label label, String columnName) throws SQLException, ClassNotFoundException {
        // Get a connection from the database connection class
        Connection connection = Dbconnection.getInstance().getConnection();

        // Prepare the statement and execute the query
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            // If a result is returned, get the count from the specified column
            if (resultSet.next()) {
                int count = resultSet.getInt(columnName);

                // Set the count to the label
                label.setText(String.valueOf(count));
            }
        }
    }
}
