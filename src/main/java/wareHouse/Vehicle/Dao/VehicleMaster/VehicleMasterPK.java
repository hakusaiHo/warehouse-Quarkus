package wareHouse.Vehicle.Dao.VehicleMaster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleMasterPK {
    public String vehicleId;

    public VehicleMasterPK() {
    }

    public VehicleMasterPK(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int hashCode() {
        int result;
        result = vehicleId.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof VehicleMasterPK))
            return false;
        final VehicleMasterPK pk = (VehicleMasterPK) other;
        if (!pk.vehicleId.equals(vehicleId))
            return false;
        return true;
    }

}
