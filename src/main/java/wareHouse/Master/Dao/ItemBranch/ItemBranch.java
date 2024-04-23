package wareHouse.Master.Dao.ItemBranch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoVersionBaseEntity;

@Entity
@Table(name = "KTST_ITEM_BRANCH")
@IdClass(ItemBranchPK.class)
@Getter
@Setter
public class ItemBranch extends AuditInfoVersionBaseEntity {
    @Id
    @Column(name = "ITEM_ID")
    public Integer itemId;
    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Column(name = "ITEM_NUMBER", length = 25)
    public String itemNumber;

    @Column(name = "LOT_CONTROL", length = 1)
    public String lotControl;
}
