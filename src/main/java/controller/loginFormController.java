package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class loginFormController {
    public TextField txtName;
    public TextField txtPassword;
    public Label labal;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String nameText = txtName.getText();
        String password = txtPassword.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        pst.setString(1,nameText);
        pst.setString(2,password);

        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashbord.fxml"))));
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!").show();
        }
    }

    public void lblRegister(MouseEvent mouseEvent) throws IOException {

        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/register_form.fxml"))));
        stage.show();
    }
}
