package pl.kpro.recipe_site.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kpro.recipe_site.data.entity.Recipe;
import pl.kpro.recipe_site.data.repository.RecipeRepository;
import pl.kpro.recipe_site.web.payload.request.AddRecipeRequest;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Service
public class RecipeService
{
    final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    public Recipe addRecipe(AddRecipeRequest addRecipeRequest, String authorUsername )
    {
        Recipe recipe = new Recipe(
                addRecipeRequest.getTitle(),
                addRecipeRequest.getImageName(),
                addRecipeRequest.getContent(),
                authorUsername,
                addRecipeRequest.getIngredients(),
                addRecipeRequest.getType(),
                addRecipeRequest.getDifficultyRating()
        );
        return recipeRepository.save(recipe);
    }
}
