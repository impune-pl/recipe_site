package pl.kpro.recipe_site.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails
{
    @Id
    private String id;

    private String password;

    @Indexed(unique = true)
    private String username;

    private List<UUID> recipes;

    private String[] roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Arrays.stream(this.roles).map(SimpleGrantedAuthority::new).collect(toList());
    }

    public List<String> getRoles()
    {
        return Arrays.stream(this.roles).collect(toList());
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.recipes = new ArrayList<>();
        this.roles = new String[]{"USER"};
    }
}
