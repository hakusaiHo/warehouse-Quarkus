package wareHouse.Area.Dao.AreaInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Entity
@Table(name = "KTST_AREA_INFO")
@Getter
@Setter
@IdClass(AreaInfoPK.class)
public class AreaInfo extends AuditInfoBaseEntity {

    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Id
    @Column(name = "AREA", length = 15)
    public String area;

    @Column(name = "AREA_NAME", length = 30)
    public String areaName;
}
