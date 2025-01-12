package controller;

import DB.DBConnection;
import Util.CrudUtill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemServices{
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
        ResultSet rst = CrudUtill.execute("SELECT * FROM item WHERE code=?",id);

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
            Connection connection = DBConnection.getInstance().getConnection();
            Statement pst = connection.createStatement();
            ResultSet resultSet = pst.executeQuery("SELECT * FROM item");

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

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        itemArrayList.forEach(items -> {
            itemObservableList.add(items);
        });

        return itemObservableList;
    }
}
