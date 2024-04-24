package wareHouse.Area.Controller;

import java.util.List;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Description;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import wareHouse.Area.Model.ItemAreaList;
import wareHouse.Area.Service.ItemAreaService;
import wareHouse.Master.Service.ItemBranchService;
import wareHouse.Master.Service.ItemService;

@GraphQLApi
public class ItemAreaFetcher {

    @Inject
    ItemAreaService itemAreaService;

    @Inject
    ItemService itemService;

    @Inject
    ItemBranchService itemBranchService;

    @Query("ItemAreaList")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseManager" })
    @Description("依據分支廠與料號回傳料品偏好保存區域")
    public List<ItemAreaList> getItemAreaList(String branch, String itemNumber) throws Exception {

        if (branch == null || branch.isEmpty() || itemNumber == null) {
            throw new Exception("請輸入欲查詢的分支廠與料號");
        }

        Integer itemId = itemService.getItemIdByItemNumber(itemNumber);
        if (itemId == null) {
            throw new Exception("欲查詢的料號不存在");
        }
        if (itemBranchService.CheckitemBranch(itemId, branch) == null) {
            throw new Exception("欲查詢的料號分支廠資訊不存在");
        }

        return itemAreaService.getItemAreaList(branch, itemId);
    }
}
