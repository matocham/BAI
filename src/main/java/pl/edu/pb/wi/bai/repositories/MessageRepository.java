package pl.edu.pb.wi.bai.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.bai.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
}
