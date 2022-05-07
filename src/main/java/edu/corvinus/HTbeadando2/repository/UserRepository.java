package edu.corvinus.HTbeadando2.repository;
import edu.corvinus.HTbeadando2.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    List<User> findAll();
}
