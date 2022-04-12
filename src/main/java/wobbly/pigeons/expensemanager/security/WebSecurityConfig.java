package wobbly.pigeons.expensemanager.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //TODO implement roles/authorities

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.invalidSessionUrl("/invalidSession.htm"));
        http
            .authorizeRequests()
                .antMatchers("/", "/registration", "/api/v1/employees/newEmployee").permitAll()
                .antMatchers("/api/**").hasRole("//TODO")
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/landingpage", true)
                .passwordParameter("password")
                .usernameParameter("username")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }


    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

//    public void configure2(AuthenticationManagerBuilder auth2) throws Exception {
//        auth2.inMemoryAuthentication().withUser("SAdmin").password("123ABC").roles("ADMIN");
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }

}
