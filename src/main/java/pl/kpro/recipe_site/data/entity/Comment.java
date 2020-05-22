package pl.kpro.recipe_site.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Data
@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment
{
    @Id
    private String id;
    @Indexed
    private UUID forRecipe;
    @Indexed(unique = true)
    private UUID uuid = UUID.randomUUID();
    private String authorUsername;
    private String commentText;
    private Date creationTimestamp;

    public Comment(UUID forRecipe,String authorUsername,String text)
    {
        this.forRecipe = forRecipe;
        this.authorUsername=authorUsername;
        this.commentText=text;
        this.creationTimestamp = new Date();
    }
}
