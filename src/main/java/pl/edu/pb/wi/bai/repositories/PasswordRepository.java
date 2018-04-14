package pl.edu.pb.wi.bai.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pb.wi.bai.models.Password;
import pl.edu.pb.wi.bai.models.User;

public interface PasswordRepository extends CrudRepository<Password, Long> {
	List<Password> findByUser(User user);
}
