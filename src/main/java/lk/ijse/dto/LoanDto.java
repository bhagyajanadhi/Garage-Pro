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
public class LoanDto {
    private String LoanId;
    private String amount;
    private String supplierId;
    private LocalDate duedate;
    private String paymentstatus;


}