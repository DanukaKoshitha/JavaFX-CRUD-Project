package controller;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemServices{
    @Override
    public boolean addItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");

            pst.setString(1,item.getCode());
            pst.setString(2,item.getDescription());
            pst.setDouble(3, Double.parseDouble(String.valueOf(item.getUnitPrice())));
            pst.setInt(4,Integer.parseInt(String.valueOf(item.getQty())));

            return pst.executeUpdate()>0;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Item searchItem(String id) {
        Item searchItemObject=null;
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE code=?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                 searchItemObject =  new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchItemObject;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            String SQL = "UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);

            pst.setString(1,item.getDescription());
            pst.setDouble(2, Double.parseDouble(String.valueOf(item.getUnitPrice())));
            pst.setInt(3, Integer.parseInt(String.valueOf(item.getQty())));
            pst.setString(4,item.getCode());

            return pst.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pst.setString(1,id);

            return pst.executeUpdate()>0;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return false;
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
