package pl.edu.pb.wi.bai.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ALLOWED_MESSAGES")
public class AllowedMessages implements Serializable{
   @EmbeddedId
    AllowedMessagesPK Allowed_id;
}
