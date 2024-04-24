package wareHouse.Vehicle.Controller;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.core.Response;
import wareHouse.Master.Service.ItemService;
import wareHouse.Vehicle.Dao.VehicleType.VehicleType;
import wareHouse.Vehicle.Dao.VehicleType.VehicleTypePK;

@Path("public/vehicleType")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "vehicleType", description = "載具類別資訊管理")
public class VehicleTypeController {

    @Inject
    ItemService itemService;

    @POST
    @Transactional
    @Operation(summary = "新增載具類別資訊", description = "新增載具類別資訊至資料庫")
    public Response addVehicleTypeByPK(VehicleType vehicleType) {

        if (vehicleType.getItemId() != null && itemService.getItemMasterByItemId(vehicleType.getItemId()) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("輸入的短料號不存在").build();
        }
        vehicleType.modifiedDate = new Date();
        vehicleType.modifiedUser = 1L;
        vehicleType.persist();
        return Response.created(URI.create("/vehicleType")).entity(vehicleType).build();
    }

    @PUT
    @Transactional
    @Path("/{vehicleTypeId}")
    @Operation(summary = "更新載具類別資訊", description = "更新載具類別資訊至資料庫")
    public Response updateVehicleTypeByPK(@PathParam("vehicleTypeId") String vehicleTypeId,
            Map<String, Object> updateData) {
        String updateField = "";
        if (updateData.size() > 0) {
            updateData.put("modifiedDate", new Date());
            updateData.put("modifiedUser", 1L);
            updateField = updateData.entrySet().stream()
                    .map(entry -> entry.getKey() + "=:" + entry.getKey())
                    .collect(Collectors.joining(" , "));
            updateData.put("pk_vehicleTypeId", vehicleTypeId);
            VehicleType.update(updateField + " where vehicleTypeId =:pk_vehicleTypeId", updateData);
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Transactional
    @Path("/{vehicleTypeId}")
    @Operation(summary = "刪除載具類別資訊", description = "刪除載具類別資訊至資料庫")
    public Response deleteVehicleTypeByPK(@PathParam("vehicleTypeId") String vehicleTypeId) {
        VehicleTypePK pk = new VehicleTypePK();
        pk.vehicleTypeId = vehicleTypeId;
        VehicleType deleteRequest = VehicleType.findById(pk);
        if (deleteRequest == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        VehicleType.deleteById(pk);
        return Response.status(Response.Status.OK).build();
    }
}
