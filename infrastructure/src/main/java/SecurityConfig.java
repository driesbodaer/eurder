import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final EurderAuthenticationProvider authenticationProvider;


    public SecurityConfig(EurderAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


}
