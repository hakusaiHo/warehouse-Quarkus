package wareHouse.Area.Controller;

import java.util.List;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;
import wareHouse.Area.Service.AreaInfoService;
import wareHouse.Master.Service.ItemBranchService;

@GraphQLApi
public class AreaInfoFetcher {

    @Inject
    AreaInfoService areaInfoService;

    @Inject
    ItemBranchService itemBranchService;

    @Query
    @Description("查詢儲區主檔資訊")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    public List<AreaInfo> getAreaInfoList(String branch, String area, String areaName) throws Exception {
        if (branch != null && !branch.isEmpty()
                && itemBranchService.checkBranchExists(branch) == false) {
            throw new Exception("欲查詢的分支廠代號不存在");
        }
        return areaInfoService.getAreaInfoList(branch, area, areaName);
    }

    @Query("getAreaInfoListByBranch")
    @Description("依據分支廠列出儲區與儲區資訊")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    public List<AreaInfo> getAreaInfoListByBranch(String branch) throws Exception {
        if (branch != null && !branch.isEmpty()
                && itemBranchService.checkBranchExists(branch) == false) {
            throw new Exception("欲查詢的分支廠代號不存在");
        }
        return areaInfoService.getAreaInfoListByBranch(branch);
    }
}
