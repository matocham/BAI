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
    Integer messageId;
    @Column(name = "TEXT")
    String text;
    @Column(name = "MOD")
    @JoinColumn(foreignKey = @ForeignKey(name = "USERS_FK"))
    @ManyToMany(targetEntity = User.class)
    Set<User> moderatorId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<User> getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Set<User> moderatorId) {
        this.moderatorId = moderatorId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", moderatorId=" + moderatorId +
                '}';
    }
}
