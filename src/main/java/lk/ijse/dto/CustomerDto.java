package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {
    private String customerId;
    private String customerName;
    private String customerContactInfromation;
    private String customerAddress;
    private String customerEmail;


}
