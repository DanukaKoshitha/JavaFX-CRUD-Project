package Service.Custom;

import Service.SuperService;
import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerServices extends SuperService {
     boolean addCustomer(Customer customer) throws SQLException;

     Customer searchCustomer(String id);

     boolean updateCustomer(Customer customer);

     boolean deleteCustomer(String id);

     List<Customer> loadTable();

}
