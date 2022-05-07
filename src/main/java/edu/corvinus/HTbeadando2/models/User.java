package edu.corvinus.HTbeadando2.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;

    public User(){ }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public User(long id, String userName, String  password){
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

}
