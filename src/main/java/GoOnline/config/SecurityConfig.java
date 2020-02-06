package GoOnline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/Images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/game").hasRole("USER").anyRequest().authenticated()
                .antMatchers("/stomp/**", "/stompEndpoint", "/stomp").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/game/lobby")
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/login")
                .and()
                .rememberMe().tokenValiditySeconds(1209600);
    }

}
