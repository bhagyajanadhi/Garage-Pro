package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryDto {
    private String inventoryId;
    private String description;
    private String supplierId;
    private String partName;
    private int qty;
    private double unitePrice;


}
