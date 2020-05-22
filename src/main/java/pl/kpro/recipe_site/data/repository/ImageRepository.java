package pl.kpro.recipe_site.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.kpro.recipe_site.data.entity.Image;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public interface ImageRepository extends MongoRepository<Image,String>
{
    Optional<Image> findAllByName(UUID name);
}
