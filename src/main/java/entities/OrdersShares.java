package entities;

import java.io.Serializable;

/**
 * Transit entity to connect Orders with their Shares (M:M) relation
 *
 * @author Dmitry Tochilin
 */
public class OrdersShares implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Order order;
    private Share share;

    public OrdersShares() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order Order) {
        this.order = order;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Long getOrderId() {
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
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "entities.OrdersShares[ id = " + id + " ]";
    }

}
