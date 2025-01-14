package controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField lblDate;
    public TextField lblTime;

    CustomerServices services = new CustomerController();

    public void btnSearchOnAction(ActionEvent actionEvent){
        Customer customer = services.searchCustomer(txtID.getText());

        if (customer!=null){
                txtID.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtSalary.setText(String.valueOf(customer.getSalary()));
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Found!").show();
        }
    }

    public void loadDateAndTime() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDate.setText(currentDate.format(dateFormatter));


        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        if (
                services.addCustomer(
                        new Customer(
                            txtID.getText(),
                            txtName.getText(),
                            txtAddress.getText(),
                            Double.parseDouble(txtSalary.getText())
                        )
                )
        ){
            new Alert(Alert.AlertType.CONFIRMATION,"Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"NOT Added!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent){

        if (services.updateCustomer(
                new Customer(
                        txtID.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        )){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        if (services.deleteCustomer(txtID.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again!").show();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/viewCustomerForm.fxml"))));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
    }
}
