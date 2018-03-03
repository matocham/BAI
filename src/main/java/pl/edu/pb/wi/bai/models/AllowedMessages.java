package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ALLOWED_MESSAGES")
public class AllowedMessages implements Serializable{
    @Id
    @JoinColumn(foreignKey = @ForeignKey(name = "MSG_ID_FK"))
    @ManyToOne(targetEntity = Message.class)
    @Column(name = "MESSAGE_ID")
    Message message;
    @Id
    @JoinColumn(foreignKey = @ForeignKey(name="USR_ID_FK"))
    @ManyToOne(targetEntity = User.class)
    @Column (name = "USER_ID")
    User user;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AllowedMessages{" +
                "message=" + message +
                ", user=" + user +
                '}';
    }
}
