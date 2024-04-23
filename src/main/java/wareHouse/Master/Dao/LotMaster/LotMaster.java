package wareHouse.Master.Dao.LotMaster;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import wareHouse.Master.AuditInfoVersionBaseEntity;

@Getter
@Setter
@Entity
@Table(name = "KTST_LOT_MASTER")
@IdClass(LotMasterPK.class)
public class LotMaster extends AuditInfoVersionBaseEntity {

    @Id
    @Column(name = "ITEM_ID")
    public Integer itemId;

    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Id
    @Column(name = "LOT_NUMBER", length = 30)
    public String lotNumber;

    @NonNull
    @Column(name = "ITEM_NUMBER", length = 25)
    public String itemNumber;

    @Column(name = "EXPIRED_DATE")
    public Date expiredDate;

    @Column(name = "SUPPLIER_NUMBER")
    public Long supplierNumber;

}
