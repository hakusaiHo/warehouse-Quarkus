package wareHouse.Master.Dao.LotMaster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotMasterPK {
    public Integer itemId;
    public String branch;
    public String lotNumber;

    public LotMasterPK() {
    }

    public LotMasterPK(Integer itemId, String branch, String lotNumber) {
        this.itemId = itemId;
        this.branch = branch;
        this.lotNumber = lotNumber;
    };

    public int hashCode() {
        return itemId.hashCode() + branch.hashCode() + lotNumber.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof LotMasterPK)) {
            return false;
        }

        final LotMasterPK pk = (LotMasterPK) other;
        if (!pk.itemId.equals(itemId)) {
            return false;
        }
        if (!pk.branch.equals(branch)) {
            return false;
        }
        if (!pk.lotNumber.equals(lotNumber)) {
            return false;
        }
        return true;
    }

}
