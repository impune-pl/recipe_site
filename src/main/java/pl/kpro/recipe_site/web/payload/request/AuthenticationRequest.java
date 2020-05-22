package pl.kpro.recipe_site.web.payload.request;

import lombok.Data;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Data
public class AuthenticationRequest
{
    private String username;
    private String password;
}
