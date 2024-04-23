package wareHouse.Vehicle.Dao.VehicleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleTypePK {

    public String vehicleTypeId;

    public VehicleTypePK() {
    }

    public VehicleTypePK(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public int hashCode() {
        int result;
        result = vehicleTypeId.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof VehicleTypePK))
            return false;
        final VehicleTypePK pk = (VehicleTypePK) other;
        if (!pk.vehicleTypeId.equals(vehicleTypeId))
            return false;
        return true;
    }
}
