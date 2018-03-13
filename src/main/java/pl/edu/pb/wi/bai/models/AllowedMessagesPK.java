package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by justys on 05.03.2018.
 */
@Embeddable
public class AllowedMessagesPK implements Serializable{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "USER_ID")
    User userId;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Message.class)
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
}
