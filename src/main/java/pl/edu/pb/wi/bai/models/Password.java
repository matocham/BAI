package pl.edu.pb.wi.bai.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORDS")
public class Password {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASS_SEQ")
    @SequenceGenerator(name = "PASS_SEQ", sequenceName = "pass_seq")
    @Column(name = "PASS_ID")
    Long id;

    @Column(name = "MASK")
    String mask;
    @Column(name = "PASSWORD_HASH")
    String password;
    
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "USERS_P__PK"))
    @ManyToOne(targetEntity = User.class)
    User user;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		Password other = (Password) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Password [id=" + id + ", mask=" + mask + ", password=" + password + ", user=" + user + "]";
	}
	
}
