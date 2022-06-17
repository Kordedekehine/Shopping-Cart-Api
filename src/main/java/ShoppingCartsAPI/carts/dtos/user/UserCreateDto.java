package ShoppingCartsAPI.carts.dtos.user;

import ShoppingCartsAPI.carts.model.Role;

import javax.validation.constraints.NotBlank;

public class UserCreateDto {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    @NotBlank
    private Role role;
    @NotBlank
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
