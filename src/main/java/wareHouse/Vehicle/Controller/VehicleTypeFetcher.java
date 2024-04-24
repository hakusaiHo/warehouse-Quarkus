package wareHouse.Vehicle.Controller;

import java.util.List;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import jakarta.inject.Inject;
import wareHouse.Vehicle.Dao.VehicleType.VehicleType;
import wareHouse.Vehicle.Service.VehicleTypeService;

@GraphQLApi
public class VehicleTypeFetcher {

    @Inject
    VehicleTypeService vehicleTypeService;

    @Query("VehicleType")
    @Description("依據載具類別或類別明細取得載具類別中所有資料")
    public List<VehicleType> getVehicleTypeList(String vehicleTypeId, String vehicleTypeSpec) {
        return vehicleTypeService.findVehicleTypeList(vehicleTypeId, vehicleTypeSpec);
    }

}
