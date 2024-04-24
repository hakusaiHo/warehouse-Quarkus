package wareHouse.Area.Controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Dao.AreaInfo.AreaInfo;
import wareHouse.Area.Dao.ItemArea.ItemArea;
import wareHouse.Area.Dao.ItemArea.ItemAreaPK;
import wareHouse.Area.Model.ItemAreaList;
import wareHouse.Area.Service.AreaInfoService;
import wareHouse.Area.Service.ItemAreaService;
import wareHouse.Master.Controller.ItemBranchFetcher;
import wareHouse.Master.Service.ItemBranchService;
import wareHouse.Master.Service.ItemService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

@Path("public/ItemArea")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ItemArea", description = "料品偏好保存設定區域")
// @Authenticated
public class ItemAreaController {

    @Inject
    EntityManager em;

    @Inject
    ItemBranchFetcher itemBranchFetcher;

    @Inject
    ItemService itemService;

    @Inject
    ItemAreaService itemAreaService;

    @Inject
    ItemBranchService itemBranchService;

    @Inject
    AreaInfoService areaInfoService;

    @POST
    @Transactional
    @Path("/itemArea")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseManager" })
    @Operation(summary = "新增料品偏好保存區域資訊")
    public Response addItemAreaByPK(ItemArea itemArea) throws Exception {

        if (itemArea.getBranch() == null || itemArea.getBranch().isEmpty() || itemArea.getItemId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("請輸入分支廠號或料號").build();
        }
        if (itemArea.getItemId() != null && itemService.getItemMasterByItemId(itemArea.getItemId()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("查無此料號資訊").build();
        }
        if (itemBranchService.CheckitemBranch(itemArea.getItemId(), itemArea.getBranch()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("找不到此料品的對應分支廠資料 ").build();
        }
        List<AreaInfo> areaInfoList = areaInfoService.getAreaInfoList(null, itemArea.getPreferSaveArea(), null);
        if (areaInfoList == null || areaInfoList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區代碼不存在 ").build();
        }
        itemArea.modifiedDate = new Date();
        itemArea.modifiedUser = 1L;
        itemArea.persist();
        return Response.created(URI.create("/addItemArea")).entity("新增成功：" + itemArea).build();
    }

    @PUT
    @Transactional
    @Path("itemArea/{branch}/{itemId}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseManager" })
    @Operation(summary = "更新料品偏好保存區域")
    public Response updateItemAreaByPK(@PathParam("branch") String branch,
            @PathParam("itemId") Integer itemId, Map<String, Object> updateData) {

        if (updateData.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("更新資料不可為空").build();
        }
        long updateCount = ItemArea.count("branch = ?1 and itemId = ?2", branch, itemId);
        if (updateCount == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("要更新的資料不存在").build();
        }
        String preferSaveArea = (String) updateData.get("preferSaveArea");
        if (preferSaveArea != null) {
            List<AreaInfo> areaInfoList = areaInfoService.getAreaInfoList(null, preferSaveArea, null);
            if (areaInfoList == null || areaInfoList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("此儲區代碼不存在 ").build();
            }
        }
        updateData.put("modifiedDate", new Date());
        updateData.put("modifiedUser", 1L);
        String updateQuery = updateData.entrySet().stream().map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" , "));
        updateData.put("pk_branch", branch);
        updateData.put("pk_itemId", itemId);
        ItemArea.update(updateQuery + " WHERE branch =:pk_branch AND itemId =:pk_itemId", updateData);
        return Response.status(Response.Status.OK).entity("更新成功：" + updateData).build();
    }

    @DELETE
    @Path("/itemArea/{branch}/{itemId}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseManager" })
    @Transactional
    @Operation(summary = "刪除料品偏好保存區域資訊")
    public Response deleteItemAreaByPK(@PathParam("branch") String branch,
            @PathParam("itemId") Integer itemId) {

        ItemAreaPK pk = new ItemAreaPK();
        pk.setBranch(branch);
        pk.setItemId(itemId);
        boolean isDeleted = ItemArea.deleteById(pk);
        if (!isDeleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("找不到要刪除的資料").build();
        }
        return Response.ok("刪除資料成功").build();
    }

    @DELETE
    @Path("/itemArea/bulk")
    @Transactional
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseManager" })
    @Operation(summary = "刪除料品偏好保存區域列表")
    public Response deleteItemAreaList(List<ItemAreaList> deleteRequest) {
        return itemAreaService.deleteItemAreaList(deleteRequest);
    }
}