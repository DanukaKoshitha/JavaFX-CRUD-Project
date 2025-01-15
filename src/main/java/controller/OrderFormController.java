package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.AddtoCart;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public TextField txtAddress;
    public TextField txtSalary;
    public ComboBox comboCusID;
    public ComboBox comboItemID;
    public TextField txtQTYOnHand;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtName;
    public Label txtTotal;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQTY;
    public TableColumn colTotal;
    public Label lblDateSet;
    public TextField txtQTY;
    public Label lblTimeSet;
    public TableView table;

    public void loadDateAndTime() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDateSet.setText(currentDate.format(dateFormatter));


        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTimeSet.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void setComboCusID(){

        ObservableList<String> customerObservableList = FXCollections.observableArrayList();

        for(Customer customer : CustomerController.getInstance().loadTable()){
            customerObservableList.add(customer.getId());
        }

        comboCusID.setItems(customerObservableList);

    }

    public void setItemCode(){
        ObservableList<String> itemObservableList= FXCollections.observableArrayList();

        for(Item item : ItemController.getInstance().loadTable()){
            itemObservableList.add(item.getCode());
        }

        comboItemID.setItems(itemObservableList);
    }

    ObservableList<AddtoCart> addtoCarts = FXCollections.observableArrayList();

    public void btnAddOnAction(ActionEvent actionEvent) {
        Double Total = Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtQTY.getText());
        addtoCarts.add(
                new AddtoCart(
                        comboItemID.getValue().toString(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQTY.getText()),
                        Total
                )
        );

        System.out.println(addtoCarts);
        table.setItems(addtoCarts);
        calcNetTotal();
    }

    private void calcNetTotal(){
        Double netTotal=0.0;
        for (AddtoCart cartTM: addtoCarts){
            netTotal+=cartTM.getTotal();
        }
        txtTotal.setText(netTotal.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        setComboCusID();
        setItemCode();

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        comboCusID.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null){
                setValue((String)t1);
            }
        });

        comboItemID.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1 != null){
                try {
                    setItemValues((String) t1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setItemValues(String id) throws SQLException {
        Item item = ItemController.getInstance().searchItem(id);

        txtDescription.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQTYOnHand.setText(String.valueOf(item.getQty()));
    }

    public void setValue(String id){
        Customer customer = CustomerController.getInstance().searchCustomer(id);

        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtSalary.setText(String.valueOf(customer.getSalary()));
    }

    public void btnPlaceOnAction(ActionEvent actionEvent) {

    }
}
