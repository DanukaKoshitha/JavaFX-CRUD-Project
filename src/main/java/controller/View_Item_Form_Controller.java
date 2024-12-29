package controller;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View_Item_Form_Controller implements Initializable {

    public TableView tblView;
    @FXML
    private TableColumn  colCode;

    @FXML
    private TableColumn  colDescription;

    @FXML
    private TableColumn  colPrice;

    @FXML
    private TableColumn  colQTY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadTabel();
    }


    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTabel();
    }

    public void loadTabel(){
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
            throw new RuntimeException(e);
        }

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        itemArrayList.forEach(items -> {
            itemObservableList.add(items);
        });

        tblView.setItems(itemObservableList);
    }
}
