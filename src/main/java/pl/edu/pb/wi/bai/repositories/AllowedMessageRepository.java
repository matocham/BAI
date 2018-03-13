package pl.edu.pb.wi.bai.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.bai.models.AllowedMessage;

import java.util.List;

public interface AllowedMessageRepository extends CrudRepository<AllowedMessage, Long> {

    List<AllowedMessage> findByAllowedId_MessageId_MessageId(Long messageId);
}
