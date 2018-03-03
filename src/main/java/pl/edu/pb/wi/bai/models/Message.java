package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "MESSAGE")
@Entity
public class Message implements Serializable {
    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer messageId;
    @Column(name = "TEXT")
    String text;
    @Column(name = "MOD")
    @JoinColumn(foreignKey = @ForeignKey(name = "USERS_FK"))
    @ManyToOne(targetEntity = User.class)
    User moderatorId;

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

    public User getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(User moderatorId) {
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
