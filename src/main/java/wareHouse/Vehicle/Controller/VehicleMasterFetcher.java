package wareHouse.Vehicle.Controller;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import jakarta.inject.Inject;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMaster;
import wareHouse.Vehicle.Service.VehicleService;

@GraphQLApi
public class VehicleMasterFetcher {
    @Inject
    VehicleService vehicleService;

    @Query
    @Description("檢查載具主檔資訊是否存在")
    public boolean existsByVehicleId(String vehicleId) {
        return vehicleService.existsByVehicleId(vehicleId);
    }

    @Query("VehicleMaster")
    @Description("依據載具號取得載具主檔資料")
    public List<VehicleMaster> getVehicleMasterList(String vehicleId) throws Exception {
        if (vehicleId != null && !vehicleId.trim().isEmpty() && !vehicleService.existsByVehicleId(vehicleId)) {
            throw new Exception("找不到這個載具號碼: " + vehicleId);
        }
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            return VehicleMaster.listAll();
        }
        return VehicleMaster.list("vehicleId", vehicleId);
    }
}
