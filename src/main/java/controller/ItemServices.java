package controller;

import model.Item;

import java.util.List;

public interface ItemServices {
    boolean addItem(Item item);

    Item searchItem(String id);

    boolean updateItem(Item item);

    boolean deleteItem(String id);

    List<Item> loadTable();
}
