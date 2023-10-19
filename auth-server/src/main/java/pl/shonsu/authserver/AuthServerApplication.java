package pl.shonsu.authserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    ApplicationRunner userDetailsManager(UserDetailsManager userDetailsManager) {

        return args -> {
            UserDetails sho = User.builder()
                    .username("shonsu")
                    .password(passwordEncoder().encode("pw"))
                    .roles("admin")
                    .build();
            if (!userDetailsManager.userExists(sho.getUsername())) {
                userDetailsManager.createUser(sho);
            }
        };

//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("pw"))
//                .roles("user")
//                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
