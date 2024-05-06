package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobDto {
    private String jobId;
    private String employeeId;
    private String description;
    private String date;
    private String vehicleId;


}