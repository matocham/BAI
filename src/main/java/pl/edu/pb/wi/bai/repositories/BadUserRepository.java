package pl.edu.pb.wi.bai.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.edu.pb.wi.bai.models.BadUser;

public interface BadUserRepository extends CrudRepository<BadUser, Long> {
	List<BadUser> findAll();

	BadUser findByUsername(String username);

}
