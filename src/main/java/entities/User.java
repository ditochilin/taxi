package entities;

import java.io.Serializable;

/**
 * Describes user's (admins, clients, taxi drivers) params (name (login or nickname,
 * phone, password, role))
 *
 * @author Dmitry Tochilin
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String phone;
    private String password;
    private String userName;
    private Role role;

    public User() {
    }

    public User(String name, String password) {
        this.userName = name;
        this.password = password;
    }

    public User(String phone, String password, String userName, Role role) {
        this.phone = phone;
        this.password = password;
        this.userName = userName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        if(role==null){
            return null;
        }
        return role.getId();
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.intValue() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return (this.id != null ||
                other.id == null) &&
                (this.id == null ||
                        this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "User[ userName=" + userName +" ]";
    }

}
