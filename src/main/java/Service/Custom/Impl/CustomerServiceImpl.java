package Service.Custom.Impl;

import DB.DBConnection;
import Entity.CustomerEntity;
import Repository.Custom.CustomerDao;
import Repository.DaoFactory;
import Repository.SuperDao;
import Service.Custom.CustomerServices;
import Util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DTO.Customer;
import org.modelmapper.ModelMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerServices {

    public static CustomerServiceImpl insance;

    public CustomerServiceImpl(){

    }

    public static CustomerServiceImpl getInstance() {
        return insance == null ? insance = new CustomerServiceImpl() : insance;
    }

    @Override
    public boolean addCustomer(Customer customer) {

        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);

        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

        try {
            customerDao.save(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
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


