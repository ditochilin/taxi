package entities;

import java.io.Serializable;

/**
 * An Entity that aggregates taxi driver and car (with car type and price)
 * Holds name (brand, model), car state number identifier
 * Shows current stage of car (busy/not busy). If not busy - can take new order
 *
 * @author Dmitry Tochilin
 */
public class Taxi implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String carName;
    private String carNumber;
    private boolean busy;
    private CarType carType;
    private User driver;

    public Taxi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public boolean getBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Long getDriverId() {
        return driver.getId();
    }

    public Long getCarTypeId() {
        if(carType==null){
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
        if (!(object instanceof Taxi)) {
            return false;
        }
        Taxi other = (Taxi) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Taxi[ carNumber = "+carNumber+" ]";
    }

}
