package wareHouse.Area.Dao.ItemArea;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemAreaPK {
    public Integer itemId;
    public String branch;

    public ItemAreaPK(String branch, Integer itemId) {
        this.itemId = itemId;
        this.branch = branch;
    }

    public ItemAreaPK() {
    }

    @Override
    public int hashCode() {
        int result;
        result = branch.hashCode() + itemId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ItemAreaPK))
            return false;

        final ItemAreaPK pk = (ItemAreaPK) other;

        if (!pk.branch.equals(branch))
            return false;
        if (!pk.itemId.equals(itemId))
            return false;
        return true;
    }

}
