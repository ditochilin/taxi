package entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Describe type of taxi car, Depending on this type Order's cost is calculated
 * Type name (example Coupe, Sedan, Electric Sedan...)
 * In price the cost of one kilometer is fixed for each type
 *
 * @author Dmitry Tochilin
 */
public class CarType implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String typeName;
    private BigDecimal price;

    public CarType() {
    }

    public CarType(Long id, String typeName, BigDecimal price) {
        this.id = id;
        this.typeName = typeName;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int hashCode() {
        return (id!= null) ? id.intValue() : 0;

    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CarType)) {
            return false;
        }
        CarType other = (CarType) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "CarType[ typeName = "+typeName+" ]";
    }

}
