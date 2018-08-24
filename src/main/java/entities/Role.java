package entities;

import java.io.Serializable;

/**
 * Role describes type of user and cause user's right in system
 *
 * @author Dmitry Tochilin
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String description;
    private String roleName;

    public Role() {
    }

    public Role(String description, String roleName) {
        this.description = description;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Role[ roleName=" + roleName + " ]";
    }

}
