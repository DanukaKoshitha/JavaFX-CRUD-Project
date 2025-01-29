package controller;

import Service.Custom.Impl.ItemServiceImpl;
import Service.Custom.ItemServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
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
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        ItemServices services=new ItemServiceImpl();
        for (Item item : services.loadTable()) {
            itemObservableList.add(item);
        }

        tblView.setItems(itemObservableList);
    }
}
