package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Document that fixes start stage of client's ordering taxi service (i.e. set up Start address,
 * End address (destination) )
 *
 * @author Dmitry Tochilin
 */
public class TaxiOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Date orderDate;
    private String startPoint;
    private String endPoint;
    private Integer distance;
    private BigDecimal cost;
    private Time feedTime;
    private Taxi taxi;
    private User user;
    private Status status;
    private List<Share> shares;

    public TaxiOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idOrder) {
        this.id = idOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Time getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(Time feedTime) {
        this.feedTime = feedTime;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTaxiId() {
        return taxi.getId();
    }

    public Long getUserId() {
        return user.getId();
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public void addShare(Share share){
        shares.add(share);
    }

    public boolean removeShare(Share share){
        return shares.remove(share);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TaxiOrder)) {
            return false;
        }
        TaxiOrder other = (TaxiOrder) object;
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
        return "entities.TaxiOrder[ id=" + id + " ]";
    }

}
