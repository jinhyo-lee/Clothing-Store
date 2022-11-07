package com.prj.config;

import com.prj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/dashboard/**")
                .authenticated().antMatchers("/dashboard/**").hasAnyRole("MEMBER", "ADMIN")
                .anyRequest().permitAll()
        .and()
                .formLogin()
                .loginPage("/member/login").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("id")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard?orderPage=1&mileagePage=1", true)
                .failureHandler(failureHandler())
                .permitAll()
        .and()
                .rememberMe()
                .tokenValiditySeconds(86400 * 3).key("secretKey")
                .alwaysRemember(false)
                .userDetailsService(memberService)
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/mainPage")
        .and()
                .exceptionHandling()
                .accessDeniedPage("/error");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthFailureHandler failureHandler() {
        return new AuthFailureHandler();
    }
}
