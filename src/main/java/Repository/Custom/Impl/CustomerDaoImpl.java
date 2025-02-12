package Repository.Custom.Impl;

import DB.DBConnection;
import Entity.CustomerEntity;
import Repository.Custom.CustomerDao;
import DTO.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
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
    public boolean update(String s, CustomerEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<CustomerEntity> getAll() {
        return List.of();
    }
}
