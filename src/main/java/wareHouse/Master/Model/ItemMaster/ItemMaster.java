package wareHouse.Master.Model.ItemMaster;

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
@IdClass(ItemMasterPK.class)
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

    @Column(name = "stock_days", length = 6)
    public Integer stockDays = 0;

    @Column(name = "primary_uom", length = 2)
    public String primaryUom = "";

    @Column(name = "secondary_uom", length = 2)
    public String secondaryUom = "";

    @Column(name = "purchase_uom", length = 2)
    public String purchaseUom = "";

    @Column(name = "pricing_uom", length = 2)
    public String pricingUom = "";

    @Column(name = "shipping_uom", length = 2)
    public String shippingUom = "";

    @Column(name = "production_uom", length = 2)
    public String productionUom = "";

    @Column(name = "allocation_uom", length = 2)
    public String allocationUom = "";

    @Column(name = "weight_uom", length = 2)
    public String weightUom = "";

    @Column(name = "volume_uom", length = 2)
    public String volumeUom = "";

    @Column(name = "glcategory", length = 4)
    public String glCategory = "";

    @Column(name = "stocking_type", length = 1)
    public String stockingType = "";

    @Column(name = "buyer")
    public Long buyer;

    @Column(name = "address_number_planner")
    public Long addressNumberPlanner;
}