package wareHouse.Master.Controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import wareHouse.Master.Dao.ItemBranch.ItemBranch;
import wareHouse.Master.Dao.ItemBranch.ItemBranchPK;
import wareHouse.Master.Service.ItemService;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
// @Authenticated
public class ItemBranchFetcher {

    @Inject
    ItemService itemService;

    @Query
    @Description("查詢料品分支主檔資訊")
    @RolesAllowed({ "AppDeveloper", "AppManager", "VehicleUser", "WarehouseManager", "WarehouseKeeper" })
    public List<ItemBranch> getItemBranchByPK(Integer itemId, String branch) throws Exception {
        ItemBranchPK pk = new ItemBranchPK();
        pk.branch = branch;
        pk.itemId = itemId;
        ItemBranch foundData = ItemBranch.findById(pk);
        if (foundData == null) {
            throw new Exception("找不到料品分支廠資料: " + "料號：" + itemId + " 分支廠：" + branch);
        }
        return ItemBranch.list("branch =?1 and itemId =?2", branch, itemId);
    }
}
