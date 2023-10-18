package pl.shonsu.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    InMemoryUserDetailsManager userDetailsManager() {
        UserDetails sho = User.builder()
                .username("shonsu")
                .password(passwordEncoder().encode("pw"))
                .roles("admin")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("pw"))
                .roles("user")
                .build();

        return new InMemoryUserDetailsManager(sho, user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
