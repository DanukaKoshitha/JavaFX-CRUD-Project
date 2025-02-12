package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AddtoCart {
    private String itemCode;
    private String description;
    private Double unitPrice;
    private  int qty;
    private Double total;
}
