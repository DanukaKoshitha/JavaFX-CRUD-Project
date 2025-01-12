package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class viewCustomerFormController implements Initializable {

    public TableColumn txtName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableView table;
    public TableColumn colID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        txtName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        loadTable();
    }

    public void loadTable(){
        CustomerServices services = new CustomerController();
        ObservableList<Customer> customerObservableList = (ObservableList<Customer>) services.loadTable();
        table.setItems(customerObservableList);
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
    }
}
