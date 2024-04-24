package wareHouse.Area.Controller;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;
import wareHouse.Area.Dao.AreaInfo.AreaInfoPK;
import wareHouse.Area.Dao.AreaLocation.AreaLocation;
import wareHouse.Area.Dao.ItemArea.ItemArea;
import wareHouse.Area.Service.AreaInfoService;
import wareHouse.Area.Service.AreaLocationService;
import wareHouse.Area.Service.ItemAreaService;
import wareHouse.Master.Controller.ItemBranchFetcher;
import wareHouse.Master.Service.ItemBranchService;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("public/AreaInfo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "AreaInfo", description = "儲區設定檔")
public class AreaInfoController {

    @Inject
    EntityManager em;

    @Inject
    AreaInfoService areaInfoService;

    @Inject
    ItemBranchFetcher itemBranchFetcher;

    @Inject
    ItemBranchService itemBranchService;

    @Inject
    ItemAreaService itemAreaService;

    @Inject
    AreaLocationService areaLocationService;

    @POST
    @Transactional
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "新增儲區設定檔資訊")
    public Response addAreaInfoByPK(AreaInfo areaInfo) throws Exception {

        if (areaInfo.getBranch() != null
                && itemBranchService.checkBranchExists(areaInfo.getBranch()) == false) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區的分支廠代號不存在").build();
        }

        areaInfo.modifiedDate = new Date();
        areaInfo.modifiedUser = 1L;
        areaInfo.persist();
        return Response.created(URI.create("areaInfo")).entity(areaInfo).build();
    }

    @PUT
    @Transactional
    @Path("/{branch}/{area}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "更新儲區設定資料", description = "更新儲區設定資料至資料庫")
    public Response updateAreaInfoByPK(@PathParam("branch") String branch,
            @PathParam("area") String area,
            Map<String, Object> updateData) {

        if (branch == null || branch.isEmpty() || area == null || area.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("更新資料不可為空").build();
        }
        if (branch != null
                && itemBranchService.checkBranchExists(branch) == false) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區的分支廠代號不存在").build();
        }
        long updateCount = AreaInfo.count("branch = ?1 and area = ?2", branch, area);
        if (updateCount == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("要更新的資料不存在").build();
        }
        updateData.put("modifiedDate", new Date());
        updateData.put("modifiedUser", 1L);
        String updateQuery = updateData.entrySet().stream()
                .map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(", "));
        updateData.put("pk_branch", branch);
        updateData.put("pk_area", area);
        AreaInfo.update(updateQuery + " WHERE branch = :pk_branch AND area = :pk_area", updateData);
        return Response.status(Response.Status.OK).entity(updateData).build();
    }

    @DELETE
    @Path("/{branch}/{area}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Transactional
    @Operation(summary = "刪除儲區設定資料")
    public Response deleteAreaInfoByPK(@PathParam("branch") String branch, @PathParam("area") String area) {

        List<ItemArea> itemAreaList = itemAreaService.checkItemAreaList(branch, area);
        if (!itemAreaList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區已設定為偏好保存區域，請先移除設定，再進行刪除動作。").build();
        }

        List<AreaLocation> areaLocationList = areaLocationService.getAreaLocationList(branch, area, null, null);
        if (!areaLocationList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區已設定對照的儲位資料，請先移除設定，再進行刪除動作。").build();
        }

        AreaInfoPK pk = new AreaInfoPK();
        pk.setBranch(branch);
        pk.setArea(area);
        boolean areaInfoIsDeleted = AreaInfo.deleteById(pk);
        if (!areaInfoIsDeleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("找不到要刪除的儲區資料").build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/bulk")
    @Transactional
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "刪除儲區設定資料列表")
    public Response deleteAreaInfoList(List<AreaInfoPK> deleteRequest) {
        return areaInfoService.deleteAreaInfoList(deleteRequest);
    }
}
