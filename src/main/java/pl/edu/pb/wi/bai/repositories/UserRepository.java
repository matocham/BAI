package pl.edu.pb.wi.bai.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pb.wi.bai.models.User;

import java.util.List;

/**
 * Created by justys on 05.03.2018.
 */
public interface UserRepository extends CrudRepository<User,Long>{
    List<User> findAll();
}

