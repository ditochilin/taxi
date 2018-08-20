package entities;

import java.io.Serializable;

/**
 * Transit entity to connect TaxiOrders with their Shares (M:M) relation
 *
 * @author Dmitry Tochilin
 */
public class OrdersShares implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private TaxiOrder order;
    private Share share;

    public OrdersShares() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaxiOrder getOrder() {
        return order;
    }

    public void setOrder(TaxiOrder Order) {
        this.order = order;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Long getTaxiOrderId() {
        return order.getId();
    }

    public Long getShareId() {
        return share.getId();
    }

    @Override
    public int hashCode() {
        return (id!= null) ? id.intValue() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrdersShares)) {
            return false;
        }
        OrdersShares other = (OrdersShares) object;
        if ((this.id == null &&
                other.id != null) ||
                (this.id != null &&
                        !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OrdersShares[ id = " + id + " ]";
    }

}
