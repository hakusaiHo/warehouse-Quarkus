package wareHouse.Vehicle.Controller;

import java.util.List;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import jakarta.inject.Inject;
import wareHouse.Vehicle.Model.VehicleList;
import wareHouse.Vehicle.Service.VehicleDetailService;
import wareHouse.Vehicle.Service.VehicleService;

@GraphQLApi
public class VehicleDetailFetcher {

    @Inject
    VehicleDetailService vehicleDetailService;

    @Inject
    VehicleService vehicleService;

    @Query("VehicleDetailList")
    @Description("依據載具號、料號、批號列出該載具的明細資料")
    public List<VehicleList> getVehicleDetailList(String vehicleId, String itemNumber, String lotNumber) {
        return vehicleDetailService.getVehicleDetailList(vehicleId, itemNumber, lotNumber);
    }
}
