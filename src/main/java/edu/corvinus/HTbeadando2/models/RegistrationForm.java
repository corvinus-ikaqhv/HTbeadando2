package edu.corvinus.HTbeadando2.models;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Pattern;

public class RegistrationForm {
    private String name;

    @Length(min = 6)
    private String userName;

    @Length(min = 8)
    @Pattern(regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{8,}$")
    private String password;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }
}
