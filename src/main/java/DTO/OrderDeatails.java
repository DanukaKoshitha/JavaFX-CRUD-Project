package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDeatails {
    private String orderID;
    private String itemCode;
    private int qty;
    private Double unitPrice;
}
