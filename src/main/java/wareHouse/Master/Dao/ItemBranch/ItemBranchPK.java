package wareHouse.Master.Dao.ItemBranch;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBranchPK implements Serializable {
    public ItemBranchPK() {
    };

    public String branch;
    public Integer itemId;

    public ItemBranchPK(String pk_branch, Integer pk_itemId) {
        this.branch = pk_branch;
        this.itemId = pk_itemId;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ItemBranchPK)) {
            return false;
        }
        final ItemBranchPK pk = (ItemBranchPK) other;
        if (!pk.branch.equals(branch)) {
            return false;
        }
        if (!pk.itemId.equals(itemId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return branch.hashCode() + itemId.hashCode();
    }
}