package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USERS")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "users_seq")
    @Column(name = "USER_ID")
    Long id;

    @Column(name = "LOGIN")
    String username;
    @Column(name = "PASSWORD_HASH")
    String password;
    @Column(name = "LAST_LOGIN")
    Date lastLoginDate;

    @Column(name="FAILED_LOGINS")
    Integer loginAttempts;
    
    @Column(name= "FAILED_LOGIN_DATE")
    Date lastFailedLogin;
    
    @Column(name="MAX_LOGIN_ATT")
    Integer maxLoginAttempts;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Date getLastFailedLogin() {
		return lastFailedLogin;
	}

	public void setLastFailedLogin(Date lastFailedLogin) {
		this.lastFailedLogin = lastFailedLogin;
	}

	public Integer getMaxLoginAttempts() {
		return maxLoginAttempts;
	}

	public void setMaxLoginAttempts(Integer maxLoginAttempts) {
		this.maxLoginAttempts = maxLoginAttempts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", lastLoginDate="
				+ lastLoginDate + ", loginAttempts=" + loginAttempts + ", lastFailedLogin=" + lastFailedLogin + "]";
	}
    
	
}
