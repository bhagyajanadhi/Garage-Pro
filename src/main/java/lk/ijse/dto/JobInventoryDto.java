package lk.ijse.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class JobInventoryDto {
    private String inventoryId;
    private String description;
    private String partName;
    private int stockLevel;
    private double unitePrice;
    private int qty;
    private double totalPrice;
}