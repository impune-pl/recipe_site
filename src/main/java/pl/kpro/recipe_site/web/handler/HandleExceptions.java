package pl.kpro.recipe_site.web.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kpro.recipe_site.web.exception.NotFoundException;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@ControllerAdvice
public class HandleExceptions
{
    @ExceptionHandler(NotFoundException.class)
    public String handle()
    {
        return "forward:/";
    }
}
