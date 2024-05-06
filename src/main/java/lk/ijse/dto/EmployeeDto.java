package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private String employeeId;
    private String jobId;
    private double salary;
    private String name;
    private String skills;
    private String contactInfromation;
  

}
