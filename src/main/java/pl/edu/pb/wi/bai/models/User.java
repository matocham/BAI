package pl.edu.pb.wi.bai.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
	@SequenceGenerator(name = "USERS_SEQ", sequenceName = "users_seq")
	@Column(name = "USER_ID")
	Long id;

	@Column(name = "LOGIN")
	String username;

	@Column(name = "LAST_LOGIN")
	Date lastLoginDate;

	@Column(name = "FAILED_LOGINS")
	Integer loginAttempts;

	@Column(name = "FAILED_LOGIN_DATE")
	Date lastFailedLogin;

	@Column(name = "MAX_LOGIN_ATT")
	Integer maxLoginAttempts;

	@OneToOne()
	@JoinColumn(name = "CURRENT_MASK")
	Password currentPassword;

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

	public Password getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(Password currentPassword) {
		this.currentPassword = currentPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", lastLoginDate=" + lastLoginDate + ", loginAttempts="
				+ loginAttempts + ", lastFailedLogin=" + lastFailedLogin + ", maxLoginAttempts=" + maxLoginAttempts
				+ ", currentMask=" + currentPassword + "]";
	}

}
