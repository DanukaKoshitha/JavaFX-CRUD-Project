package Repository.Custom;

import Entity.CustomerEntity;
import Repository.CrudDao;
import DTO.Customer;

public interface CustomerDao extends CrudDao<CustomerEntity,String> {

}
