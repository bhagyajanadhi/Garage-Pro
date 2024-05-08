package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDto {
    private String vehicleId;
    private String vehicleModel;
    private String vehicleLicensePlate;
    private String cus_id;


}