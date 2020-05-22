package pl.kpro.recipe_site.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.kpro.recipe_site.data.entity.Comment;
import pl.kpro.recipe_site.data.entity.Recipe;
import pl.kpro.recipe_site.data.repository.CommentRepository;
import pl.kpro.recipe_site.data.repository.RecipeRepository;
import pl.kpro.recipe_site.data.service.RecipeService;
import pl.kpro.recipe_site.web.exception.NotFoundException;
import pl.kpro.recipe_site.web.payload.request.AddCommentRequest;
import pl.kpro.recipe_site.web.payload.request.AddRecipeRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@RestController
@RequestMapping("/api/recipe")
public class RecipeController
{
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, CommentRepository commentRepository,RecipeService recipeService)
    {
        this.recipeRepository=recipeRepository;
        this.commentRepository = commentRepository;
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> index(@RequestParam("type") Optional<String> type,
                              @RequestParam("authorName") Optional<String> authorName,
                              @RequestParam("rating") Optional<Integer> rating)
    {
        if(type.isPresent() && !type.get().equals("") && authorName.isPresent() && !authorName.get().equals("") && rating.isPresent())
        {
            return recipeRepository.findAllByTypeIsLikeAndAuthorUsernameIsLikeAndDifficultyRatingEquals(type.get(), authorName.get(), rating.get());
        }
        else if(type.isPresent() && !type.get().equals("") && authorName.isPresent() && !authorName.get().equals(""))
        {
            return recipeRepository.findAllByTypeIsLikeAndAuthorUsernameIsLike(type.get(), authorName.get());
        }
        else if(type.isPresent() && !type.get().equals("") && rating.isPresent())
        {
            return recipeRepository.findAllByTypeIsLikeAndDifficultyRatingEquals( type.get(), rating.get());
        }
        else if(rating.isPresent() && authorName.isPresent() && !authorName.get().equals(""))
        {
            return recipeRepository.findAllByDifficultyRatingEqualsAndAuthorUsernameIsLike( rating.get(),authorName.get());
        }
        else if(type.isPresent() && !type.get().equals(""))
        {
            return recipeRepository.findAllByTypeIsLike(type.get());
        }
        else if(authorName.isPresent() && !authorName.get().equals(""))
        {
            return recipeRepository.findAllByAuthorUsernameIsLike(authorName.get());
        }
        else if(rating.isPresent())
        {
            return recipeRepository.findAllByDifficultyRatingEquals(rating.get());
        }
        else
            return recipeRepository.findAll();
    }

    @GetMapping("/{uuid}")
    public Recipe details(@PathVariable UUID uuid)
    {
        return recipeRepository.findByUuid(uuid).orElseThrow(()-> new NotFoundException("No recipe with such title"));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AddRecipeRequest addRecipeRequest, @AuthenticationPrincipal UserDetails userDetails)
    {
        try
        {
            Recipe newRecipe = recipeService.addRecipe(addRecipeRequest, userDetails.getUsername());
            return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @PostMapping("comment")
    public ResponseEntity<Comment> comment(@RequestBody AddCommentRequest commentRequest, @AuthenticationPrincipal UserDetails userDetails)
    {

        try
        {
            Comment newComment;
            int commentCount = commentRepository.countByForRecipe(commentRequest.getRecipeUuid());
            if(commentCount >= 5)
            {
                Comment toDelete = commentRepository.findTopByForRecipeOrderByCreationTimestampAsc(commentRequest.getRecipeUuid());
                commentRepository.delete(toDelete);
            }
            newComment = new Comment(
                commentRequest.getRecipeUuid(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                commentRequest.getCommentText()
            );
            commentRepository.save(newComment);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Recipe> delete(@PathVariable UUID uuid)
    {
        try
        {
            var optionalToDelete = recipeRepository.findByUuid(uuid);
            if(optionalToDelete.isPresent())
            {
                Recipe toDelete = optionalToDelete.get();
                recipeRepository.delete(toDelete);
                return new ResponseEntity<>(toDelete, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("comment/{uuid}")
    public ResponseEntity<Comment> deleteComment(@PathVariable UUID uuid)
    {
        try
        {
            var optionalToDelete = commentRepository.findByUuid(uuid);
            if(optionalToDelete.isPresent())
            {
                Comment toDelete = optionalToDelete.get();
                commentRepository.delete(toDelete);
                return new ResponseEntity<>(toDelete, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{uuid}/comments")
    public List<Comment> loadComments(@PathVariable UUID uuid)
    {
        return commentRepository.findAllByForRecipe(uuid);
    }
}
