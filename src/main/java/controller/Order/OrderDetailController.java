package controller.Order;

import DB.DBConnection;
import DTO.OrderDeatails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetails(List<OrderDeatails> orderDeatails) throws SQLException {
        for(OrderDeatails orderDeatailsObject : orderDeatails){
            boolean isadded = addOrderDetails(orderDeatailsObject);
            if (!isadded){
                return false;
            }
        }
        return true;
    }

    public boolean addOrderDetails(OrderDeatails orderDeatails) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("Insert into orderdetail values (?,?,?,?)");

        pst.setString(1,orderDeatails.getOrderID());
        pst.setString(2,orderDeatails.getItemCode());
        pst.setString(3, String.valueOf(orderDeatails.getQty()));
        pst.setString(4, String.valueOf(orderDeatails.getUnitPrice()));

        return pst.executeUpdate()>0;
    }
}
