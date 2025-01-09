package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomerFormController {

    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE id=?");
        pst.setString(1,txtID.getText());
        ResultSet resultSet = pst.executeQuery();

        Customer customer = null;

        if (resultSet.next()){
            customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }

        if (customer!=null){
            txtID.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }

    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("insert into customer values(?,?,?,?)");

        pst.setString(1,txtID.getText());
        pst.setString(2,txtName.getText());
        pst.setString(3,txtAddress.getText());
        pst.setDouble(4, Double.parseDouble(txtSalary.getText()));

        if (pst.executeUpdate()>0){
            new Alert(Alert.AlertType.CONFIRMATION,"Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Not Added!").show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? where id=?");

        pst.setString(1,txtName.getText());
        pst.setString(2,txtAddress.getText());
        pst.setDouble(3, Double.parseDouble(txtSalary.getText()));
        pst.setString(4,txtID.getText());

        if (pst.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("DELETE FROM customer where id=?");
        pst.setString(1,txtID.getText());

        if (pst.executeUpdate()>0){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again!").show();
        }
    }

    public void btnViewOnAction(ActionEvent actionEvent) {
    }
}
