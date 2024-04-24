package wareHouse.Area.Controller;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;
import wareHouse.Area.Dao.AreaLocation.AreaLocation;
import wareHouse.Area.Service.AreaInfoService;
import wareHouse.Area.Service.AreaLocationService;
import wareHouse.Master.Service.ItemBranchService;

@GraphQLApi
public class AreaLocationFetcher {

    @Inject
    AreaLocationService areaLocationService;

    @Inject
    ItemBranchService itemBranchService;

    @Inject
    AreaInfoService areaInfoService;

    @Query
    @Description("查詢儲區儲位對應檔資訊")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    public List<AreaLocation> getAreaLocationList(String branch, String area, String location, String locationDesc)
            throws Exception {
        if (branch != null && !branch.isEmpty()
                && itemBranchService.checkBranchExists(branch) == false) {
            throw new Exception("欲查詢的分支廠代號不存在");
        }
        List<AreaInfo> areaInfoList = areaInfoService.getAreaInfoList(branch, area, null);
        if (areaInfoList.isEmpty()) {
            throw new Exception("指定的儲區不存在");
        }
        return areaLocationService.getAreaLocationList(branch, area, location, locationDesc);
    }
}
