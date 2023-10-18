package pl.shonsu.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@EnableMethodSecurity
@SpringBootApplication
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}

@Service
class GreetingsService {

    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public Map<String, String> greet() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("message", "Hello " + jwt.getSubject());
    }
}

@Controller
@ResponseBody
class GreetingController {

    private final GreetingsService service;

    GreetingController(GreetingsService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    Map<String, String> hello(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("message", "Hello " + jwt.getSubject());
    }

    @GetMapping("/hello-method")
    Map<String, String> hello() {
        return service.greet();
    }
}