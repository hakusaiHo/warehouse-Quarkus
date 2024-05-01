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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wareHouse.Master.Dao.ItemMaster.ItemMaster;
import wareHouse.Master.Service.ItemBranchService;
import wareHouse.Master.Service.ItemService;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMaster;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMasterPK;
import wareHouse.Vehicle.Model.Vehicle;
import wareHouse.Vehicle.Service.VehicleService;
import wareHouse.Vehicle.Service.VehicleTypeService;

@Path("public/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "VehicleMaster", description = "載具主檔管理")
public class VehicleMasterController {
    @Inject
    VehicleService vehicleService;

    @Inject
    ItemService itemService;

    @Inject
    VehicleTypeService vehicleTypeService;

    @Inject
    ItemBranchService itemBranchService;

    @Inject
    EntityManager em;

    // 同時新增 vehicleMaster 與 vehicleDetail
    @POST
    @Transactional
    @Operation(summary = "新增載具主檔資訊與承載明細", description = "新增載具主檔資訊與承載明細至資料庫")
    public Response addVehicleMasterDetailByPK(Vehicle vehicle) {

        Response validationResponse = validateVehicleMaster(vehicle.vehicleMaster);
        if (validationResponse != null) {
            return validationResponse;
        }
        vehicle.vehicleMaster.modifiedDate = new Date();
        vehicle.vehicleMaster.modifiedUser = 1L;
        vehicle.vehicleMaster.persist();

        vehicle.vehicleDetail.forEach(detail -> {
            if (detail.getItemId() != null) {
                ItemMaster checkitem = itemService.getItemMasterByItemId(detail.getItemId());
                if (checkitem == null) {
                    throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
            }
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

        Response validationResponse = validateVehicleMaster(vehicleMaster);
        if (validationResponse != null) {
            return validationResponse;
        }
        vehicleMaster.modifiedDate = new Date();
        vehicleMaster.modifiedUser = 1L;
        if (vehicleMaster.getPauseCode() == null || vehicleMaster.getPauseCode().isEmpty()) {
            vehicleMaster.setPauseCode("00");
        }
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

    private Response validateVehicleMaster(VehicleMaster vehicleMaster) {
        if (vehicleService.existsByVehicleId(vehicleMaster.getVehicleId())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (vehicleTypeService.findVehicleTypeList(vehicleMaster.getVehicleTypeId(), null).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (itemBranchService.checkBranchExists(vehicleMaster.getBranch()) == false) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return null;
    }
}
