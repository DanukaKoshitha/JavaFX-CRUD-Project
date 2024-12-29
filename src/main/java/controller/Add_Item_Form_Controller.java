package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Add_Item_Form_Controller {

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQTY;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");

            pst.setString(1,txtCode.getText());
            pst.setString(2,txtDescription.getText());
            pst.setDouble(3, Double.parseDouble(txtUnitPrice.getText()));
            pst.setInt(4,Integer.parseInt(txtQTY.getText()));

            if (pst.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pst.setString(1,txtCode.getText());

            if (pst.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        pst.setString(1, txtCode.getText());
        ResultSet resultSet = pst.executeQuery();

       if (resultSet.next()){
                txtDescription.setText(resultSet.getString("description"));
                txtUnitPrice.setText(String.valueOf((resultSet.getDouble("unitPrice"))));
                txtQTY.setText(String.valueOf(resultSet.getInt("qtyOnHand")));
       }else {
                new Alert(Alert.AlertType.ERROR,"Item Not Found!").show();
       }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            String SQL = "UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);

            pst.setString(1,txtDescription.getText());
            pst.setDouble(2, Double.parseDouble(txtUnitPrice.getText()));
            pst.setInt(3, Integer.parseInt(txtQTY.getText()));
            pst.setString(4,txtCode.getText());

            if (pst.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Update Successful!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnViewOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/view_item_form.fxml"))));
        stage.show();
    }

}
