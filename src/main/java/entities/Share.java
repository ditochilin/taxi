package entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Describes two types of shares: discount according to loyalty program and/or simple share that
 * decreases cost of a trip (in percent of cost or sum)
 * Loyalty program means a discount received by client depending on the cost of his trip
 * (example: 100 uah and more - 3% discount; 150 uah and more - 5%; 180 and more - 7%)
 * A simple Share may be set or not  (not mandatory)  (isOn flag). If Share isOn==true, than it will
 * be applied in all Orders
 * Loyalty applied by default (onOff = true)
 *
 * @author Dmitry Tochilin
 */
public class Share implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String shareName;
    private Boolean isLoyalty;
    private Boolean isOn;
    private BigDecimal sum;
    private Float percent;

    public Share(Long id, String shareName, Boolean isLoyalty, Boolean isOn, BigDecimal sum, Float percent) {
        this.id = id;
        this.shareName = shareName;
        this.isLoyalty = isLoyalty;
        this.isOn = isOn;
        this.sum = sum;
        this.percent = percent;
    }

    public Share(String shareName, boolean isLoyalty, boolean isOn, BigDecimal sum, float percent) {
        this.shareName = shareName;
        this.isLoyalty = isLoyalty;
        this.isOn = isOn;
        this.sum = sum;
        this.percent = percent;
    }

    public Share() {

    }

    public Boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(Boolean on) {
        isOn = on;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public Boolean getIsLoyalty() {
        return isLoyalty;
    }

    public void setIsLoyalty(Boolean isLoyalty) {
        this.isLoyalty = isLoyalty;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Share)) {
            return false;
        }
        Share other = (Share) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Share[ name = " + shareName + " ]";
    }

}
