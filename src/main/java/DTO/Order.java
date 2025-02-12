package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private String id;
    private String date;
    private String customerID;

    private List<OrderDeatails> orderDeatails;
}
