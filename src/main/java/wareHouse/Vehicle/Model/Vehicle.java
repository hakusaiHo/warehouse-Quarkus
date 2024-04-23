package wareHouse.Vehicle.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import wareHouse.Vehicle.Dao.VehicleDetail.VehicleDetail;
import wareHouse.Vehicle.Dao.VehicleMaster.VehicleMaster;

@Getter
@Setter
public class Vehicle {
    public VehicleMaster vehicleMaster;
    public List<VehicleDetail> vehicleDetail;
}