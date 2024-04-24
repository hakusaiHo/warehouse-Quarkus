package wareHouse.Area.Dao.ItemArea;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wareHouse.Master.AuditInfoBaseEntity;

@Entity
@Table(name = "KTST_ITEM_AREA")
@Getter
@Setter
@IdClass(ItemAreaPK.class)
public class ItemArea extends AuditInfoBaseEntity {

    @Id
    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Id
    @Column(name = "ITEM_ID")
    public Integer itemId;

    @Column(name = "PREFER_SAVE_AREA", length = 15)
    public String preferSaveArea;
}
