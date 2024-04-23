package wareHouse.Area.Dao.OrderHeader;

import java.io.Serializable;

public class OrderHeaderPK implements Serializable {
  public String orderCompany;
  public Long orderNumber;
  public String orderType;
  public String orderSuffix;

  public OrderHeaderPK() {
  }

  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof OrderHeaderPK))
      return false;
    final OrderHeaderPK pk = (OrderHeaderPK) other;
    if (!pk.orderCompany.equals(orderCompany))
      return false;
    if (!pk.orderNumber.equals(orderNumber))
      return false;
    if (!pk.orderType.equals(orderType))
      return false;
    if (!pk.orderSuffix.equals(orderSuffix))
      return false;
    return true;
  }

  public int hashCode() {
    return orderCompany.hashCode() + orderNumber.hashCode()
        + orderType.hashCode() + orderSuffix.hashCode();
  }
}