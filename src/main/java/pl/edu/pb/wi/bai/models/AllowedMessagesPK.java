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
    @JoinColumn(name = "User_ID")
    Set<User> userId;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Message.class)
    @JoinColumn(name = "MESSAGE_ID")
    Set<User> messageId;
}
