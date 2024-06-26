package wareHouse.Vehicle.Dao.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Getter
@Setter
@Table(name = "KTST_VEHICLE_TYPE")
@Entity
@IdClass(VehicleTypePK.class)
public class VehicleType extends AuditInfoBaseEntity {

    @Id
    @Column(name = "VEHICLE_TYPE_ID", length = 10)
    public String vehicleTypeId;

    @NonNull
    @Column(name = "ITEM_ID")
    public Integer itemId;

    @Column(name = "VEHICLE_TYPE_SPEC", length = 30)
    public String vehicleTypeSpec;

}
