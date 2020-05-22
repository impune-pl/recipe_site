package pl.kpro.recipe_site.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import pl.kpro.recipe_site.security.CustomUserDetailsService;
import pl.kpro.recipe_site.security.JwtTokenProvider;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService,JwtTokenProvider jwtTokenProvider)
    {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        super.configure(auth);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected UserDetailsService userDetailsService()
    {
        return this.userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeRequests(authorize -> authorize
                .antMatchers("/api/admin/**").hasAuthority("ROLE_ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "/api/recipe").hasAuthority("ROLE_ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "/api/recipe/**").hasAuthority("ROLE_ADMINISTRATOR")
                .antMatchers(HttpMethod.POST, "/api/recipe/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/recipe").authenticated()
                .antMatchers(HttpMethod.POST, "/api/image").authenticated()
                .antMatchers("/api/logout").authenticated()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/**").permitAll()
            ).csrf(csrf->csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .apply(new JwtAuthenticationConfiguration(jwtTokenProvider));
    }
}
