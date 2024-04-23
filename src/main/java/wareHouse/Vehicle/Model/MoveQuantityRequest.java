package wareHouse.Vehicle.Model;

import java.util.List;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveQuantityRequest {

    public List<SourceDetail> sources;
    public String targetVehicleId;

    @Getter
    @Setter
    public static class SourceDetail {
        private String vehicleId;
        private Integer itemId;
        private String lotNumber;
        private BigDecimal quantityToMove = BigDecimal.ZERO;
        private String storeUom;
    }

}
