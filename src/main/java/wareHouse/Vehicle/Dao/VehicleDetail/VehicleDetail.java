package wareHouse.Vehicle.Dao.VehicleDetail;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Getter
@Setter
@Table(name = "KTST_VEHICLE_DETAIL")
@Entity
@IdClass(VehicleDetailPK.class)
public class VehicleDetail extends AuditInfoBaseEntity {

    @Id
    @Column(name = "VEHICLE_ID", length = 30)
    public String vehicleId;

    @Id
    @Column(name = "ITEM_ID")
    public Integer itemId;

    @Id
    @Column(name = "LOT_NUMBER", length = 30)
    public String lotNumber;

    @Column(name = "STORE_UOM", length = 2)
    public String storeUom;

    @Column(name = "STORE_QUANTITY", precision = 15, scale = 4)
    public BigDecimal storeQuantity;

}
