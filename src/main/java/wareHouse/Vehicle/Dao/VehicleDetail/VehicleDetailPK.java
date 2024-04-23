package wareHouse.Vehicle.Dao.VehicleDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDetailPK {

    public String vehicleId;
    public Integer itemId;
    public String lotNumber;

    public VehicleDetailPK() {
    }

    public VehicleDetailPK(String vehicleId, Integer itemId, String lotNumber) {
        this.vehicleId = vehicleId;
        this.itemId = itemId;
        this.lotNumber = lotNumber;
    }

    public int hashCode() {
        int result;
        result = vehicleId.hashCode() + itemId.hashCode() + lotNumber.hashCode();
        return result;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof VehicleDetailPK))
            return false;
        final VehicleDetailPK pk = (VehicleDetailPK) other;
        if (!pk.vehicleId.equals(vehicleId))
            return false;
        if (!pk.itemId.equals(itemId))
            return false;
        if (!pk.lotNumber.equals(lotNumber))
            return false;
        return true;
    }

}
