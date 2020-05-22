package pl.kpro.recipe_site.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kpro.recipe_site.data.entity.User;

import java.util.Optional;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface UserRepository extends MongoRepository<User, String>
{
    Optional<User> findByUsernameLike(String username);
}
