package pl.edu.pb.wi.bai.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.bai.models.AllowedMessage;
import pl.edu.pb.wi.bai.models.Message;
import pl.edu.pb.wi.bai.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface AllowedMessageRepository extends CrudRepository<AllowedMessage, Long> {

    List<AllowedMessage> findByAllowedId_MessageId_MessageId(Long messageId);
    AllowedMessage findByAllowedId_MessageIdAndAllowedId_UserId(Message message, User user);
    @Transactional
    void deleteAllowedMessageByAllowedId_MessageIdAndAllowedId_UserId(Message message, User user);
    @Transactional
    void deleteAllowedMessageByAllowedId_MessageId(Message message);
}
