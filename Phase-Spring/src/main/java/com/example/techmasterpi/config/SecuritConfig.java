package com.example.techmasterpi.config;


import com.example.techmasterpi.domain.Oauth2GoogleLogin;
import com.example.techmasterpi.domain.Role;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.roletype;
/*import com.example.techmasterpi.security.JwtAuthenticationFilter;*/
import com.example.techmasterpi.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecuritConfig {


    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;



    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {



        httpSecurity.csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("**").permitAll()
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/login/oauth2/google").permitAll()
                .antMatchers("/user/add-user").hasAuthority(roletype.ADMIN.name())
                .antMatchers("/user/adduserwithoutimage").permitAll()
                .antMatchers("/user//retrieve-all-usersr").hasAuthority(roletype.ADMIN.name())
                .antMatchers("/user//retrieve-user/{user-id}").hasAuthority(roletype.ADMIN.name())
                .antMatchers("/user//remove-user/{user-id}").hasAuthority(roletype.ADMIN.name())
                .antMatchers("/user/updateUser").hasAuthority(roletype.ADMIN.name())
                .antMatchers("/user/UpdatePassword/{oldPassword}/{newPassword}").hasAnyAuthority(roletype.ADMIN.name()
                        ,roletype.CLIENT.name(),roletype.LESSOR.name(), roletype.MOVER.name(),roletype.CONTRACTOR.name(),roletype.SELLER.name())
                .antMatchers("/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

/*
    private ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    private OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("[votre client ID Google]")
                .clientSecret("[votre client secret Google]")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("email", "profile")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://accounts.google.com/o/oauth2/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }

   private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
        return userRequest -> {
            Oauth2GoogleLogin userGoogleInfo = new Oauth2GoogleLogin();
            OAuth2User user = new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")), userGoogleInfo.getAttributes(), userGoogleInfo.getNameAttributeKey());
            return user;
        };
    }*/



}
