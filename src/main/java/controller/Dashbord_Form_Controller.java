package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashbord_Form_Controller implements Initializable {

    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public AnchorPane loadForm;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        loadCustomerForm();
    }

    public void loadCustomerForm() throws IOException {
        URL resource = this.getClass().getResource("/view/add_customer_form.fxml");

        assert resource  != null;

        Parent load = FXMLLoader.load(resource);
        this.loadForm.getChildren().clear();
        this.loadForm.getChildren().add(load);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        URL url = this.getClass().getResource("/view/add_item_form.fxml");

        assert url != null;

        Parent load = FXMLLoader.load(url);
        this.loadForm.getChildren().clear();
        this.loadForm.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadCustomerForm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
