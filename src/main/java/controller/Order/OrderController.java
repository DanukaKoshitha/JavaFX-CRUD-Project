package controller.Order;

import DB.DBConnection;
import controller.Item.ItemController;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException {
        String SQL = "INSERT INTO ORDERS VALUES (?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();

        try {

            /////////////  set Transaction  ////////////////
            connection.setAutoCommit(false);
            ////////////////////////////////////////////////

            PreparedStatement pst = connection.prepareStatement(SQL);

            pst.setString(1,order.getId());
            pst.setString(2, String.valueOf(order.getDate()));
            pst.setString(3,order.getCustomerID());

            boolean isAdded = pst.executeUpdate()>0;

            if (isAdded){
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetails(order.getOrderDeatails());
                if (isOrderDetailAdd){
                    boolean isUpdateStock = new ItemController().updateStock(order.getOrderDeatails());
                    if (isUpdateStock){
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
