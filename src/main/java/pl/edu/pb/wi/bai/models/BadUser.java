package pl.edu.pb.wi.bai.models;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BAD_USERS")
public class BadUser implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAD_USERS_SEQ")
    @SequenceGenerator(name = "BAD_USERS_SEQ", sequenceName = "bad_users_seq")
    @Column(name = "USER_ID")
    Long id;

    @Column(name = "LOGIN")
    String username;
   
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
	
	@Transient
	public String getPassword() {
		return UUID.randomUUID().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastFailedLogin == null) ? 0 : lastFailedLogin.hashCode());
		result = prime * result + ((loginAttempts == null) ? 0 : loginAttempts.hashCode());
		result = prime * result + ((maxLoginAttempts == null) ? 0 : maxLoginAttempts.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BadUser other = (BadUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastFailedLogin == null) {
			if (other.lastFailedLogin != null)
				return false;
		} else if (!lastFailedLogin.equals(other.lastFailedLogin))
			return false;
		if (loginAttempts == null) {
			if (other.loginAttempts != null)
				return false;
		} else if (!loginAttempts.equals(other.loginAttempts))
			return false;
		if (maxLoginAttempts == null) {
			if (other.maxLoginAttempts != null)
				return false;
		} else if (!maxLoginAttempts.equals(other.maxLoginAttempts))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BadUser [id=" + id + ", username=" + username + ", loginAttempts=" + loginAttempts
				+ ", lastFailedLogin=" + lastFailedLogin + ", maxLoginAttempts=" + maxLoginAttempts + "]";
	}
    
    
}
