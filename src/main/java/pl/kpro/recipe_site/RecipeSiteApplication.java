package pl.kpro.recipe_site;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kpro.recipe_site.data.entity.User;
import pl.kpro.recipe_site.data.repository.UserRepository;

import java.util.Arrays;

@SpringBootApplication
public class RecipeSiteApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(RecipeSiteApplication.class, args);
    }

    @Bean
    public ApplicationRunner userInitializer(UserRepository repository, PasswordEncoder passwordEncoder) {
        if( ! repository.findByUsernameLike("test").isPresent())
            return args -> repository.saveAll(Arrays.asList(
                new User("test", passwordEncoder.encode("1234test")).setRoles(new String[]{"ROLE_ADMINISTRATOR"})
            ));
        else
            return (args) -> {System.out.println("User already in database");};
    }

}
