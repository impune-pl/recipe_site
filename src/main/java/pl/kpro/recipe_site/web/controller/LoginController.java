package pl.kpro.recipe_site.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kpro.recipe_site.data.repository.UserRepository;
import pl.kpro.recipe_site.security.JwtTokenProvider;
import pl.kpro.recipe_site.web.payload.request.AuthenticationRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@RestController
@RequestMapping("/api/login")
public class LoginController
{
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    PasswordEncoder passwordEncoder;
    UserRepository users;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository users)
    {
        this.users=users;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest data)
    {
        try
        {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(
                    username,
                    this.users.findByUsernameLike(username).orElseThrow(() ->
                         new UsernameNotFoundException("Username " + username + "not found")
                    ).getRoles()
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        }
        catch (AuthenticationException e)
        {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
