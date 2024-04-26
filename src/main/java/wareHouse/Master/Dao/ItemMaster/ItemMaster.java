package wareHouse.Master.Dao.ItemMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoVersionBaseEntity;

@Setter
@Getter
@Entity
@Table(name = "KTST_ITEM_MASTER")
public class ItemMaster extends AuditInfoVersionBaseEntity {

    @Id
    @Column(name = "item_id")
    public Integer itemId;

    @Column(name = "item_number", length = 25)
    public String itemNumber = "";

    @Column(name = "description1", length = 30)
    public String description1 = "";

    @Column(name = "description2", length = 30)
    public String description2 = "";

    @Column(name = "ALLERGEN_MARK")
    public Boolean allergenMark;

    @Column(name = "MUSLIM_MARK")
    public Boolean muslimMark;

    @Column(name = "SALES_CATEGORY_CODE", length = 3)
    public String categoryCode;

}