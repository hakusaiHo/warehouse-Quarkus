package main.java.skyHo.master;

import java.util.Date;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "KTST_ORDER_HEADER")
@IdClass(OrderHeaderPK.class)
public class OrderHeader extends AuditInfoBaseEntity {

    @Column(name = "ORDER_COMPANY", length = 5)
    @Id
    public String orderCompany;

    @Column(name = "ORDER_NUMBER")
    @Id
    public Long orderNumber;

    @Column(name = "ORDER_TYPE", length = 2)
    @Id
    public String orderType;

    @Column(name = "ORDER_SUFFIX", length = 3)
    @Id
    public String orderSuffix;

    @Column(name = "BRANCH", length = 12)
    public String branch;

    @Column(name = "SUPPLIER_NUMBER")
    public Long supplierNumber;

    @Column(name = "SHIP_TO")
    public Long shipTo;

    @JsonbDateFormat("yyyy-MM-dd")
    @Column(name = "ARRIVAL_DATE")
    public Date arrivalDate;

    @Column(name = "ORDER_BY", length = 10)
    public String orderBy;
}
