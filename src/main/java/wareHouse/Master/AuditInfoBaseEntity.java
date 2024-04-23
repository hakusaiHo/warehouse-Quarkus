package wareHouse.Master;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@MappedSuperclass
public abstract class AuditInfoBaseEntity extends PanacheEntityBase {

    @JsonIgnore
    @Column(name = "MODIFIED_USER")
    public Long modifiedUser = 0L;

    @JsonIgnore
    @Column(name = "MODIFIED_DATE")
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date modifiedDate;

    @PrePersist
    protected void prePersist() {
        if (this.modifiedDate == null) {
            this.modifiedDate = new Date();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.modifiedDate = new Date();
    }
}
