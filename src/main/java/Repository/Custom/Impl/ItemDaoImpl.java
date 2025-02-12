package Repository.Custom.Impl;

import Entity.ItemEntity;
import Repository.Custom.ItemDao;
import Util.CrudUtill;

import java.sql.SQLException;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(ItemEntity item) throws SQLException {
        return CrudUtill.execute(
                "INSERT INTO item VALUES (?,?,?,?)",
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQty()
        );
    }

    @Override
    public boolean update(String s, ItemEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<ItemEntity> getAll() {
        return List.of();
    }
}
