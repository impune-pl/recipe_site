package pl.kpro.recipe_site.web.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest
{
    private String commentText;
    private UUID recipeUuid;
}
