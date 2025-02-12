package Service.Custom;

import Service.SuperService;
import DTO.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemServices extends SuperService {
    boolean addItem(Item item) throws SQLException;

    Item searchItem(String id) throws SQLException;

    boolean updateItem(Item item);

    boolean deleteItem(String id) throws SQLException;

    List<Item> loadTable();
}
