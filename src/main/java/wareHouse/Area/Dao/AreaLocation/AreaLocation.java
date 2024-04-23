package wareHouse.Area.Dao.AreaLocation;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Entity
@Table(name="KTST_AREA_LOCATION")
@Getter
@Setter
@IdClass(AreaLocationPK.class)
public class AreaLocation extends AuditInfoBaseEntity {

    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Id
    @Column(name = "LOCATION_ID", length = 20)
    public String locationId;
    
    @Column(name = "LOCATION_DESC", length = 15)
    public String locationDesc;

    @Column(name = "AREA", length = 15)
    public String area;

    @Column(name = "IS_VIRTUAL")
    public boolean isVirtual = false;
}
