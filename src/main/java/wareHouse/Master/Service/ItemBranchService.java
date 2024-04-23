package wareHouse.Master.Service;

import jakarta.enterprise.context.ApplicationScoped;
import wareHouse.Master.Dao.ItemBranch.ItemBranch;
import wareHouse.Master.Dao.ItemBranch.ItemBranchPK;

@ApplicationScoped
public class ItemBranchService {

    public ItemBranch CheckitemBranch(Integer itemId, String branch) {
        ItemBranchPK pk = new ItemBranchPK();
        pk.branch = branch;
        pk.itemId = itemId;
        return ItemBranch.findById(pk);
    }

    public boolean checkBranchExists(String branch) {
        return ItemBranch.find("branch", branch).count() > 0;
    }
}
