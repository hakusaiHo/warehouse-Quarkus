package wareHouse.Vehicle.Model.VehicleMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.NonNull;
import wareHouse.Master.AuditInfoVersionBaseEntity;

@Entity
@Table(name = "KTST_VEHICLE_MASTER")
@IdClass(VehicleMasterPK.class)
public class VehicleMaster extends AuditInfoVersionBaseEntity {

    @Id
    @Column(name = "VEHICLE_ID", length = 30)
    public String vehicleId;

    @Column(name = "STORE_LOCATION")
    public String storeLocation;

    @NonNull
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @NonNull
    @Column(name = "VEHICLE_TYPE_ID")
    public String vehicleTypeId;

    @Column(name = "PAUSE_CODE", length = 2)
    public String pauseCode;
}
