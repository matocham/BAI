package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ALLOWED_MESSAGES")
public class AllowedMessage implements Serializable{
   @EmbeddedId
    AllowedMessagesPK allowedId;

    public AllowedMessagesPK getAllowedId() {
        return allowedId;
    }

    public void setAllowedId(AllowedMessagesPK allowedId) {
        this.allowedId = allowedId;
    }
}
