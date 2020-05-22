package pl.kpro.recipe_site.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import pl.kpro.recipe_site.data.entity.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface RecipeRepository extends MongoRepository<Recipe, String>
{
    List<Recipe> findAllByAuthorUsernameIsLike(String authorUsername);
    List<Recipe> findAllByDifficultyRatingEquals(int rating);
    List<Recipe> findAllByTypeIsLike(String type);

    List<Recipe> findAllByTypeIsLikeAndAuthorUsernameIsLikeAndDifficultyRatingEquals(String type, String authorUsername, int rating);

    List<Recipe> findAllByTypeIsLikeAndAuthorUsernameIsLike(String type, String authorUsername);
    List<Recipe> findAllByTypeIsLikeAndDifficultyRatingEquals(String type, int rating);
    List<Recipe> findAllByDifficultyRatingEqualsAndAuthorUsernameIsLike(int rating, String authorUsername);

    Optional<Recipe> findByUuid(UUID uuid);
}
