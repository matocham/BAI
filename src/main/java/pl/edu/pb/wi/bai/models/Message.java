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
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", moderator=" + moderator +
                '}';
    }
}
