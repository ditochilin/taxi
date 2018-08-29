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
    private boolean isLoyalty;
    private boolean isOn;
    private BigDecimal sum;
    private float percent;

    public Share() {
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean on) {
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

    public boolean getIsLoyalty() {
        return isLoyalty;
    }

    public void setIsLoyalty(boolean isLoyalty) {
        this.isLoyalty = isLoyalty;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
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
