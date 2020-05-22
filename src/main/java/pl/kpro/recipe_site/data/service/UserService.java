package pl.kpro.recipe_site.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kpro.recipe_site.data.entity.User;
import pl.kpro.recipe_site.data.repository.UserRepository;
import pl.kpro.recipe_site.web.payload.request.UserRegistrationRequest;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationRequest request)
    {
        String saltedPasswordHash = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(),saltedPasswordHash);
        return userRepository.save(user);
    }
}
