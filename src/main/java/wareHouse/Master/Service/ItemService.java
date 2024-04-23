package wareHouse.Master.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import wareHouse.Master.Dao.ItemMaster.ItemMaster;
import jakarta.inject.Inject;

@ApplicationScoped
public class ItemService {

    @Inject
    EntityManager em;

    public ItemMaster getItemMasterByItemId(Integer itemId) {
        return ItemMaster.findById(itemId);
    }

    public Integer getItemIdByItemNumber(String itemNumber) {
        ItemMaster itemMaster = ItemMaster.find("itemNumber", itemNumber).firstResult();
        return itemMaster != null ? itemMaster.itemId : null;
    }
}