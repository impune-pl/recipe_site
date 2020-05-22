package pl.kpro.recipe_site.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kpro.recipe_site.data.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface CommentRepository extends MongoRepository<Comment, String>
{
    Optional<Comment> findByUuid(UUID uuid);
    List<Comment> findAllByForRecipe(UUID forRecipe);
    Integer countByForRecipe(UUID forRecipe);
    Comment findTopByForRecipeOrderByCreationTimestampAsc(UUID forRecipe);
}
