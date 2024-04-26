package wareHouse.Area.Controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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
import wareHouse.Area.Dao.AreaLocation.AreaLocation;
import wareHouse.Area.Dao.AreaLocation.AreaLocationPK;
import wareHouse.Area.Service.AreaInfoService;
import wareHouse.Area.Service.AreaLocationService;
import wareHouse.Master.Dao.Location.Location;
import wareHouse.Master.Service.ItemBranchService;
import wareHouse.Master.Service.LocationService;

@Path("public/areaLocation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "AreaLocation", description = "儲區儲位對照檔")
public class AreaLocationController {

    @Inject
    ItemBranchService itemBranchService;

    @Inject
    AreaInfoService areaInfoService;

    @Inject
    AreaLocationService areaLocationService;

    @Inject
    LocationService locationService;

    @Inject
    EntityManager em;

    @POST
    @Transactional
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "新增儲區儲位對照檔")
    public Response addAreaLocationByPK(AreaLocation areaLocation) {

        if (areaLocation.getBranch() != null
                && itemBranchService.checkBranchExists(areaLocation.getBranch()) == false) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區的分支廠代號不存在").build();
        }
        if (areaLocation.getArea() == null || areaLocation.getArea().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("請輸入儲區代號").build();
        }
        List<AreaInfo> areaInfoList = areaInfoService.getAreaInfoList(areaLocation.getBranch(), areaLocation.getArea(),
                null);
        if (areaInfoList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("指定的儲區不存在").build();
        }
        List<Location> locationList = locationService.getLocationById(areaLocation.locationId);
        if (locationList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("輸入的儲位代碼不存在").build();
        }
        areaLocation.modifiedDate = new Date();
        areaLocation.modifiedUser = 1L;
        areaLocation.persist();
        return Response.created(URI.create("/areaLocation")).entity(areaLocation).build();
    }

    @PUT
    @Transactional
    @Path("/{branch}/{location}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "更新儲區儲位設定檔資料", description = "更新儲區儲位設定檔資料至資料庫")
    public Response updateAreaLocationByPK(@PathParam("branch") String branch, @PathParam("location") String location,
            Map<String, Object> updateData) {

        if (branch == null || branch.isEmpty() || location == null || location.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("更新資料不可為空").build();
        }
        if (branch != null
                && itemBranchService.checkBranchExists(branch) == false) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此儲區的分支廠代號不存在").build();
        }
        String area = (String) updateData.get("area");
        List<AreaInfo> areaInfoList = areaInfoService.getAreaInfoList(branch, area, null);
        if (areaInfoList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("指定的儲區不存在").build();
        }
        AreaLocation checkAreaLocationList = AreaLocation.findById(new AreaLocationPK(branch, location));
        if (checkAreaLocationList == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("要更新的資料不存在").build();
        }
        if (!checkAreaLocationList.isVirtual) {
            List<Location> locationList = locationService.getLocationList(branch, location);
            if (locationList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("輸入的儲位代碼不存在").build();
            }
        }
        updateData.put("modifiedDate", new Date());
        updateData.put("modifiedUser", 1L);
        String updateQuery = updateData.entrySet().stream()
                .map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(", "));
        updateData.put("branch", branch);
        updateData.put("location", location);
        AreaLocation.update(updateQuery + " WHERE branch = :branch AND location = :location", updateData);
        return Response.status(Response.Status.OK).entity(updateData).build();
    }

    @DELETE
    @Path("/{branch}/{location}")
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Transactional
    @Operation(summary = "刪除儲區儲位設定資料")
    public Response deleteAreaLocationByPK(@PathParam("branch") String branch, @PathParam("location") String location) {
        AreaLocation areaLocation = AreaLocation.findById(new AreaLocationPK(branch, location));
        if (areaLocation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        areaLocation.delete();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/bulk")
    @Transactional
    @RolesAllowed({ "AppDeveloper", "AppManager", "WarehouseKeeper" })
    @Operation(summary = "批次刪除儲區儲位設定資料")
    public Response deleteAreaLocationList(List<AreaLocationPK> deleteRequest) {
        return areaLocationService.deleteAreaLocationList(deleteRequest);
    }
}
