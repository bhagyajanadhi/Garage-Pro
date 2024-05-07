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
public class JobDto {
    private String jobId;
    private String employeeId;
    private String description;
    private LocalDate date;
    private String vehicleId;


}