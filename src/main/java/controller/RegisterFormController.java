package controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnRegisterOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("select * from users where email=?");
        pst.setString(1,txtEmail.getText());
        ResultSet rst = pst.executeQuery();

        if (!rst.next()){
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("insert into users (username,email,password) values(?,?,?)");

            pstm.setString(1,txtName.getText());
            pstm.setString(2,txtEmail.getText());
            pstm.setString(3,txtPassword.getText());

            pstm.executeUpdate();
        }
    }
}
