package controller.Item;

import model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemServices {
    boolean addItem(Item item) throws SQLException;

    Item searchItem(String id) throws SQLException;

    boolean updateItem(Item item);

    boolean deleteItem(String id) throws SQLException;

    List<Item> loadTable();
}
