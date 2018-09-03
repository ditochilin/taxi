package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Document that fixes start stage of client's ordering taxi service
 * (i.e. set up Start address,
 * feedTime - holds time for taxi to be at start point
 * waitingTime - how many minutes client should to wait his car
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
    private BigDecimal baseCost;
    private Integer discount;
    private Date feedTime;
    private Integer waitingTime;
    private Taxi taxi;
    private CarType carType;
    private User client;
    private Status status;

    private List<Share> shares;

    public Order() {
        this.shares = new ArrayList<>();
    }

    public Order(Long id, Date dateTime, String startPoint, String endPoint,
                 CarType carType, User client, Date feedTime) {
        this.id = id;
        this.dateTime = dateTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.carType = carType;
        this.client = client;
        this.feedTime = feedTime;
    }

    public Order(Long id, Date dateTime, String startPoint, String endPoint, Integer distance,
                 BigDecimal cost, Date feedTime, Integer waitingTime, Taxi taxi,
                 CarType carType, User client, Status status, List<Share> shares, Integer discount) {
        this.id = id;
        this.dateTime = dateTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.cost = cost;
        this.feedTime = feedTime;
        this.waitingTime = waitingTime;
        this.taxi = taxi;
        this.carType = carType;
        this.client = client;
        this.status = status;
        this.shares = shares;
        this.discount = discount;
    }

    public Order(Long id, Date dateTime, String startPoint,
                 String endPoint, Integer distance, BigDecimal cost, Date feedTime,
                 Status status, Integer waitingTime, Integer discount) {
        this.id = id;
        this.dateTime = dateTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
        this.cost = cost;
        this.feedTime = feedTime;
        this.waitingTime = waitingTime;
        this.status = status;
        this.discount = discount;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setFeedTime(Date feedTime) {
        this.feedTime = feedTime;
    }

    public Date getFeedTime() {
        return feedTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
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
        if (shares == null) {
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

    public Long getCarTypeId() {
        if (carType == null) {
            return null;
        }
        return carType.getId();
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
        return "Order[ dateTime=" + dateTime + ", client=" + client + ", carType=" + carType + ", taxi=" + taxi + "]";
    }
}
