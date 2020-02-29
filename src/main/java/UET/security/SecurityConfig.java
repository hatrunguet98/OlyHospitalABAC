package UET.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    .rememberMe().key("remember-me").tokenValiditySeconds(3600);
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/updatePassword").permitAll()
                    .antMatchers(HttpMethod.GET, "/employee/doctors").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/employee/nurses").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/employee/receptionists").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/employee/created/doctor").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/employee/created/nurse").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/employee/created/receptionist").hasRole("ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login-form")
                    .loginProcessingUrl("/login")
                    .usernameParameter("user")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login-error")
                    .and()
                    .exceptionHandling().accessDeniedPage("/403")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login-form")
                    .deleteCookies("remember-me")
                    .permitAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
