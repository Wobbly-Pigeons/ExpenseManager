package wobbly.pigeons.expensemanager.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import wobbly.pigeons.expensemanager.security.filters.CustomAuthenticationFilter;
import wobbly.pigeons.expensemanager.security.filters.CustomAuthorizationFilter;

import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
//        http.authorizeRequests().antMatchers("/api/login/**", "/api/v1/token/refresh").permitAll().antMatchers("/api/v1/**");
//        http.authorizeRequests().antMatchers("/api/v1/teachers").hasAuthority("ROLE_TEACHER");
//        http.authorizeRequests().anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        http.addFilter(customAuthenticationFilter);
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.invalidSessionUrl("/invalidSession.htm"));
        http
            .formLogin()
            .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/courses", true)
                .passwordParameter("password")
                .usernameParameter("username")
//            .and()
//                .authorizeRequests().antMatchers().hasAuthority("Manager")
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}