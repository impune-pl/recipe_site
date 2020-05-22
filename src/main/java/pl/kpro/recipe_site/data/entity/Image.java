package pl.kpro.recipe_site.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Document(collection = "images")
@Data
@NoArgsConstructor
public class Image
{
    @Id
    private String id;
    private Binary image;
    @Indexed(unique = true)
    private UUID name;

    public Image(Binary image, UUID name)
    {
        this.image=image;
        this.name=name;
    }
}
