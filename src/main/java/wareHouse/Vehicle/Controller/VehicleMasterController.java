package wareHouse.Vehicle.Controller;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import wareHouse.Master.Service.ItemService;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMaster;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMasterPK;
import wareHouse.Vehicle.Model.Vehicle;
import wareHouse.Vehicle.Service.VehicleService;
import jakarta.ws.rs.core.MediaType;

@Path("public/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "vehicleMaster", description = "載具主檔管理")
public class VehicleMasterController {
    @Inject
    VehicleService vehicleService;

    @Inject
    ItemService itemService;

    @Inject
    EntityManager em;

    // 同時新增 vehicleMaster 與 vehicleDetail
    @POST
    @Transactional
    @Operation(summary = "新增載具主檔資訊與承載明細", description = "新增載具主檔資訊與承載明細至資料庫")
    public Response addVehicleMasterDetailByPK(Vehicle vehicle) {

        if (vehicleService.existsByVehicleId(vehicle.vehicleMaster.getVehicleId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此載具編號已經存在").build();
        }

        boolean checkAllUtemId = vehicle.vehicleDetail.stream()
                .allMatch(detail -> detail.getItemId() == null
                        || itemService.getItemMasterByItemId(detail.getItemId()) != null);

        if (!checkAllUtemId) {
            return Response.status(Response.Status.BAD_REQUEST).entity("輸入的短料號不存在").build();
        }

        vehicle.vehicleMaster.modifiedDate = new Date();
        vehicle.vehicleMaster.modifiedUser = 1L;
        vehicle.vehicleMaster.persist();

        vehicle.vehicleDetail.forEach(detail -> {
            detail.modifiedDate = new Date();
            detail.modifiedUser = 1L;
            detail.persist();
        });

        return Response.status(Response.Status.CREATED).entity(vehicle).build();
    }

    @POST
    @Transactional
    @Path("/vehicleMaster")
    @Operation(summary = "新增載具主檔資訊", description = "新增載具主檔資訊至資料庫")
    public Response addVehicleMasterByPK(VehicleMaster vehicleMaster) {

        if (vehicleService.existsByVehicleId(vehicleMaster.getVehicleId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("此載具編號已經存在").build();
        }
        vehicleMaster.modifiedDate = new Date();
        vehicleMaster.modifiedUser = 1L;
        vehicleMaster.persist();
        return Response.created(URI.create("/vehicleMaster")).entity(vehicleMaster).build();
    }

    @PUT
    @Transactional
    @Path("/{vehicleId}")
    @Operation(summary = "更新載具主檔資訊", description = "更新載具主檔資訊至資料庫")
    public Response updateVehicleMasterByPK(@PathParam("vehicleId") String vehicleId,
            Map<String, Object> updateData) {
        String updateField = "";
        if (updateData.size() > 0) {
            updateData.put("modifiedDate", new Date());
            updateData.put("modifiedUser", 1L);
            updateField = updateData.entrySet().stream()
                    .map(entry -> entry.getKey() + "=:" + entry.getKey())
                    .collect(Collectors.joining(" , "));
            updateData.put("pk_vehicleId", vehicleId);
            VehicleMaster.update(updateField + " where vehicleId =:pk_vehicleId", updateData);
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Transactional
    @Path("/{vehicleId}")
    @Operation(summary = "刪除載具主檔資訊", description = "刪除載具主檔資訊至資料庫")
    public Response deleteVehicleMasterByPK(@PathParam("vehicleId") String vehicleId) {
        VehicleMasterPK pk = new VehicleMasterPK();
        pk.vehicleId = vehicleId;
        VehicleMaster deleteRequest = VehicleMaster.findById(pk);
        if (deleteRequest == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        VehicleMaster.deleteById(pk);
        return Response.status(Response.Status.OK).build();
    }
}
