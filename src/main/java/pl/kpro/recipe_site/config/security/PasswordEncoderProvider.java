package pl.kpro.recipe_site.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Configuration
public class PasswordEncoderProvider
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        //return new Argon2PasswordEncoder(16, 496, 4, 1024*1024*4, 5);
        return new BCryptPasswordEncoder();
    }
}
