package skyHo.Master;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import skyHo.Master.Util.JsonUtil;

@MappedSuperclass
public abstract class AuditInfoVersionBaseEntity extends PanacheEntityBase {

    @JsonIgnore
    @Column(name = "MODIFIED_USER")
    public Long modifiedUser = 0L;

    @JsonIgnore
    @Column(name = "MODIFIED_DATE")
    @Version
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date modifiedDate;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
