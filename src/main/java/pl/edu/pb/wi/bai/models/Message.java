package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Table(name = "MESSAGE")
@Entity
public class Message implements Serializable {
    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @SequenceGenerator(name = "MESSAGE_SEQ", sequenceName = "message_seq")
    Long messageId;

    @Column(name = "TEXT")
    String text;

    @JoinColumn(name = "MOD", foreignKey = @ForeignKey(name = "USERS_FK"))
    @ManyToOne(targetEntity = User.class)
    User moderator;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((moderator == null) ? 0 : moderator.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Message other = (Message) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (moderator == null) {
			if (other.moderator != null)
				return false;
		} else if (!moderator.equals(other.moderator))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", moderator=" + moderator +
                '}';
    }
}
