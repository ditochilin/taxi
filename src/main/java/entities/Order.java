package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Document that fixes start stage of client's ordering taxi service (i.e. set up Start address,
 * End address (destination) )
 *
 * @author Dmitry Tochilin
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Date dateTime;
    private String startPoint;
    private String endPoint;
    private Integer distance;
    private BigDecimal cost;
    private int feedTime;
    private Taxi taxi;
    private User client;
    private Status status;
    private List<Share> shares;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idOrder) {
        this.id = idOrder;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public int getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(int feedTime) {
        this.feedTime = feedTime;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Long getTaxiId() {
        if (taxi == null) {
            return null;
        }
        return taxi.getId();
    }

    public Long getClientId() {
        if (client == null) {
            return null;
        }
        return client.getId();
    }

    public List<Share> getShares() {
        if(shares==null){
            return new ArrayList<>();
        }
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public void addShare(Share share) {
        shares.add(share);
    }

    public boolean removeShare(Share share) {
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
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "entities.Order[ id=" + id + " ], client=" + client + ", taxi=" + taxi + "";
    }

}
