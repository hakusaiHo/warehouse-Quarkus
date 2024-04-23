package wareHouse.Master.Dao.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Entity
@Table(name = "KTST_LOCATION_MASTER")
@Getter
@Setter
@IdClass(LocationPK.class)
public class Location extends AuditInfoBaseEntity {

    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Id
    @Column(name = "LOCATION_ID", length = 20)
    public String locationId;

    @Column(name = "USER_RESERVED_REFERENCE", length = 15)
    public String userReservedReference;

}
