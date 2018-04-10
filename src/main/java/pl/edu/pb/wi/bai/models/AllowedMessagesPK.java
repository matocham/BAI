package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by justys on 05.03.2018.
 */
@Embeddable
public class AllowedMessagesPK implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "USER_ID")
    User userId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Message.class)
    @JoinColumn(name = "MESSAGE_ID")
    Message messageId;


    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Message getMessageId() {
        return messageId;
    }

    public void setMessageId(Message messageId) {
        this.messageId = messageId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		AllowedMessagesPK other = (AllowedMessagesPK) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
    
    
}
