package pl.kpro.recipe_site.web.payload.request;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */

import lombok.Data;

import java.util.List;

@Data
public class AddRecipeRequest
{
    private String title;
    private String content;
    private int difficultyRating;
    private String imageName;
    private String type;
    private List<String> ingredients;
}
