package wareHouse.Master.Model.ItemMaster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemMasterPK {
    public Integer itemId;

    public ItemMasterPK() {
    }

    public ItemMasterPK(Integer itemId) {
        this.itemId = itemId;
    };

    public int hashCode() {
        int result;
        result = itemId.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ItemMasterPK))
            return false;
        final ItemMasterPK pk = (ItemMasterPK) other;
        if (!pk.itemId.equals(itemId))
            return false;
        return true;
    };
}