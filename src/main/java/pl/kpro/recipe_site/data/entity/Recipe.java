package pl.kpro.recipe_site.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Data
@Document(collection = "recipes")
@AllArgsConstructor
@NoArgsConstructor
public class Recipe
{
    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    @Indexed(unique = true)
    private UUID uuid = UUID.randomUUID();

    private String imageName;

    private String content;

    private String authorUsername;

    private List<String> ingredients;

    private String type;

    private int difficultyRating;

    public Recipe(String title, String imageName, String content, String authorUsername, List<String> ingredients, String type, int difficultyRating)
    {
        this.title = title;
        this.content=content;
        this.authorUsername=authorUsername;
        this.ingredients=ingredients;
        this.imageName = imageName;
        this.type=type;
        this.difficultyRating=difficultyRating;
    }
}
