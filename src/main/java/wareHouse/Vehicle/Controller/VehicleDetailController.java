package wareHouse.Vehicle.Controller;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wareHouse.Area.Model.updateData;
import wareHouse.Master.Service.ItemService;
import wareHouse.Vehicle.Dao.VehicleDetail.VehicleDetail;
import wareHouse.Vehicle.Dao.VehicleDetail.VehicleDetailPK;
import wareHouse.Vehicle.Model.MoveQuantityRequest;
import wareHouse.Vehicle.Service.VehicleDetailService;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("public/vehicleDetail")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "VehicleDetail", description = "載具承載明細管理")
public class VehicleDetailController {

    @Inject
    EntityManager em;

    @Inject
    ItemService itemService;

    @Inject
    VehicleDetailService vehicleDetailService;

    @POST
    @Transactional
    @Operation(summary = "新增載具承載明細", description = "新增載具承載明細至資料庫")
    public Response addVehicleDetailByPK(VehicleDetail vehicleDetail) {

        if (vehicleDetail.getItemId() != null && itemService.getItemMasterByItemId(vehicleDetail.getItemId()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("輸入的短料號不存在").build();
        }
        vehicleDetail.modifiedDate = new Date();
        vehicleDetail.modifiedUser = 1L;
        vehicleDetail.persist();
        return Response.created(URI.create("/vehicleDetail")).entity(vehicleDetail).build();
    }

    @PUT
    @Transactional
    @Operation(summary = "更新載具承載明細", description = "更新載具承載明細至資料庫")
    public Response updateVehicleDetailByPK(updateData updateData) {
        Map<String, Object> params = new HashMap<>();
        params.putAll(updateData.entity);
        params.put("modifiedDate", new Date());
        params.put("modifiedUser", 1L);
        String upateField = params.entrySet().stream().map(entry -> entry.getKey() + "=:" + entry.getKey())
                .collect(Collectors.joining(" , "));
        String whereField = updateData.pk.entrySet().stream().map(entry -> entry.getKey() + "=:pk_" + entry.getKey())
                .collect(Collectors.joining(" and "));
        updateData.pk.entrySet().forEach(entry -> {
            params.put("pk_" + entry.getKey(), entry.getValue());
        });
        VehicleDetail.update(upateField + " where " + whereField, params);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/{vehicleId}/{itemId}/{lotNumber}")
    @Transactional
    @Operation(summary = "刪除載具承載明細", description = "刪除載具承載明細至資料庫")
    public Response deleteVehicleDetail(@PathParam("vehicleId") String vehicleId,
            @PathParam("itemId") Integer itemId,
            @PathParam("lotNumber") String lotNumber) {

        VehicleDetailPK pk = new VehicleDetailPK();
        pk.setVehicleId(vehicleId);
        pk.setItemId(itemId);
        pk.setLotNumber(lotNumber);

        boolean exists = VehicleDetail.findById(pk) != null;
        if (!exists) {
            return Response.status(Response.Status.NOT_FOUND).entity("找不到欲刪除的載具承載明細").build();
        }

        VehicleDetail.deleteById(pk);
        return Response.ok().build();
    }

    @DELETE
    @Path("/bulk")
    @Transactional
    @Operation(summary = "批次刪除載具承載明細")
    public Response deleteVehicleDetailList(List<VehicleDetailPK> deleteRequest) {
        return vehicleDetailService.deleteVehicleList(deleteRequest);
    }

    @POST
    @Path("/Quantity")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "置換載具明細", description = "置換載具承載明細的數量到另一個載具")
    public Response moveVehicleDetailQuantity(MoveQuantityRequest request) {

        try {
            vehicleDetailService.moveVehicleDetailQuantity(request);
            return Response.ok().entity("置換載具成功").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("置換載具失敗：" + e.getMessage()).build();
        }
    }
}