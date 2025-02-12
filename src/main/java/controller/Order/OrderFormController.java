package controller.Order;

import DB.DBConnection;
import Service.Custom.Impl.CustomerServiceImpl;
import Service.Custom.Impl.ItemServiceImpl;
import Service.Custom.Impl.OrderServiceImpl;
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
import DTO.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    public Label lblOrderID;

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

        for(Customer customer : CustomerServiceImpl.getInstance().loadTable()){
            customerObservableList.add(customer.getId());
        }

        comboCusID.setItems(customerObservableList);

    }

    public void setItemCode(){
        ObservableList<String> itemObservableList= FXCollections.observableArrayList();

        for(Item item : ItemServiceImpl.getInstance().loadTable()){
            itemObservableList.add(item.getCode());
        }

        comboItemID.setItems(itemObservableList);
    }

    ObservableList<AddtoCart> addtoCarts = FXCollections.observableArrayList();

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException {

        ///////////////  Validate QTY  Field  /////////////

        int QTYOnHand = setItemValues(comboItemID.getValue().toString()).getQty();

        if (Integer.parseInt(txtQTY.getText()) > QTYOnHand){
            new Alert(Alert.AlertType.ERROR,"Quantity exceeded stock amount").show();
            return;
        }

        /////////////////////////////////////////////////////

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

        table.setItems(addtoCarts);
        calcNetTotal();

        ///////////////  Dissable CusID Dropdown  //////////////////

        comboCusID.setDisable(true);

        //////////////  Refresh qtyOnHand  //////////////

        //ItemController.getInstance().updateStock();

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

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("SELECT MAX(id) FROM orders");
            ResultSet rst = pst.executeQuery();

            String newOrderID;
            if (rst.next()) {
                String lastOrderID = rst.getString(1); // Example: D054
                String numericPart = lastOrderID.substring(1); // Extract "054"
                int numericValue = Integer.parseInt(numericPart); // Convert "054" to 54

                int incrementedValue = numericValue + 1; // Increment to 55
                String formattedValue = String.format("%03d", incrementedValue); // Format as "055"

                newOrderID = "D" + formattedValue; // New ID: D055
            } else {
                newOrderID = "D001";
            }
            lblOrderID.setText(newOrderID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    public Item setItemValues(String id) throws SQLException {
        Item item = ItemServiceImpl.getInstance().searchItem(id);

        txtDescription.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQTYOnHand.setText(String.valueOf(item.getQty()));

        return item;
    }

    public void setValue(String id){
        Customer customer = CustomerServiceImpl.getInstance().searchCustomer(id);

        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtSalary.setText(String.valueOf(customer.getSalary()));
    }

    public void btnPlaceOnAction(ActionEvent actionEvent) {

        comboCusID.setDisable(false);

        //////////////////////////////////////

        String date = lblDateSet.getText();
        String orderId = lblOrderID.getText();
        String customerID = comboCusID.getValue().toString();

        List<OrderDeatails> orderDeatails = new ArrayList<>();

        addtoCarts.forEach(addtoCart -> {
            orderDeatails.add(new OrderDeatails(
                   lblOrderID.getText(),
                   addtoCart.getItemCode(),
                    addtoCart.getQty(),
                    addtoCart.getUnitPrice()
            ));
        });

        try {
            boolean isPlaceOrder = new OrderServiceImpl().placeOrder(new Order(orderId, date, customerID, orderDeatails));
            if (isPlaceOrder){
                new Alert(Alert.AlertType.CONFIRMATION,"Place Order").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order not placed!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
