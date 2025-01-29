package Service.Custom.Impl;

import DB.DBConnection;
import Util.CrudUtill;
import Service.Custom.ItemServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import model.OrderDeatails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemServices {

    //////////////////////////////  Singleton  /////////////////////////////////////

    public static ItemServiceImpl insance;

    public ItemServiceImpl(){

    }

    public static ItemServiceImpl getInstance()  {
        return  insance == null ? insance = new ItemServiceImpl(): insance;

    }

    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean addItem(Item item) throws SQLException {
       return CrudUtill.execute(
                "INSERT INTO item VALUES (?,?,?,?)",
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQty()
        );
    }

    @Override
    public Item searchItem(String id) throws SQLException {
        ResultSet rst = CrudUtill.execute("SELECT * FROM item WHERE code=?", id);

        Item item = null;

        if (rst.next()){
            item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        }
        return item;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            String SQL = "UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?";

            return CrudUtill.execute(
                    SQL,
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQty(),
                    item.getCode()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return CrudUtill.execute("DELETE FROM item WHERE code=?",id);
    }

    @Override
    public List<Item> loadTable() {
        ArrayList<Item> itemArrayList = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM item";

            ResultSet resultSet = CrudUtill.execute(SQL);

            while (resultSet.next()){
                itemArrayList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getDouble(3),
                                resultSet.getInt(4)
                        ));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return itemArrayList;
    }

    public boolean updateStock(List<OrderDeatails> orderDetails){
        for (OrderDeatails orderDeatailsObject : orderDetails){
            boolean isUpdateStock = updateStock(orderDeatailsObject);

            if (!isUpdateStock){
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDeatails orderDeatails){
        try {
            String SQL = "update item set qtyOnHand = qtyOnHand-? where code=?";

//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement pst = connection.prepareStatement(SQL);
//
//            pst.setString(1, String.valueOf(orderDeatails.getQty()));
//            pst.setString(2,orderDeatails.getItemCode());
//
//            return pst.executeUpdate()>0;

           return CrudUtill.execute(SQL,orderDeatails.getQty(),orderDeatails.getItemCode());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

