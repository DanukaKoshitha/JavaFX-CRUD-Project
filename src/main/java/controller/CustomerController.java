package controller;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerServices{

    public static CustomerController insance;

    CustomerController(){

    }

    public static CustomerController getInstance()  {
        return  insance == null ? insance = new CustomerController(): insance;

    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("insert into customer values(?,?,?,?)");

            pst.setString(1,customer.getId());
            pst.setString(2,customer.getName());
            pst.setString(3,customer.getAddress());
            pst.setDouble(4, customer.getSalary());

        return pst.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE id=?");
            pst.setString(1,id);
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

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? where id=?");

            pst.setString(1,customer.getName());
            pst.setString(2,customer.getAddress());
            pst.setDouble(3,customer.getSalary());
            pst.setString(4,customer.getId());

            return pst.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("DELETE FROM customer where id=?");
            pst.setString(1,id);

            return pst.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> loadTable() {
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("select * from customer");

            List<Customer> customerList = new ArrayList<>();

            while (rst.next()){
                customerList.add(
                        new Customer(
                                rst.getString(1),
                                rst.getString(2),
                                rst.getString(3),
                                rst.getDouble(4)
                        )
                );
            }
            ObservableList<Customer> observableList = FXCollections.observableArrayList();

            customerList.forEach(customer -> {
                observableList.add(customer);
            });

            return observableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }


