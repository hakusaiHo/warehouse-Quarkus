package wareHouse.Vehicle.Model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleList {
    public String vehicleId;
    public String itemNumber;
    public String lotNumber;
    public String description1;
    public Boolean allergenMark;
    public Boolean muslimMark;
    public BigDecimal storeQuantity = BigDecimal.ZERO;
    public String storeUom;
}
