package controller.Item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.sql.SQLException;

public class Add_Item_Form_Controller {

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQTY;

    @FXML
    private TextField txtUnitPrice;

    ItemServices services = new ItemController();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            if (
                    services.addItem(
                            new Item(
                                    txtCode.getText(),
                                    txtDescription.getText(),
                                    Double.parseDouble(txtUnitPrice.getText()),
                                    Integer.parseInt(txtQTY.getText())
                            )
                    )
            ){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Item Not Added!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (services.deleteItem(txtCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        try {
            Item item = services.searchItem(txtCode.getText());

            if (item != null){
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                txtQTY.setText(String.valueOf(item.getQty()));
            }else{
                new Alert(Alert.AlertType.ERROR,"Item Not Found!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (
                services.updateItem(
                new Item(
                        txtCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQTY.getText())
                )
            )
        ){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again!").show();
        }
    }

    @FXML
    void btnViewOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/view_item_form.fxml"))));
        stage.show();
    }

}
