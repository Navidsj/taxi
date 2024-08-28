package taxi.taxi.config.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import taxi.taxi.repository.UserRepository;

@Configuration
public class JwtConfiguration {

    private final UserRepository userRepository;


    public JwtConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService(){
        return email -> (org.springframework.security.core.userdetails.UserDetails) userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationMnager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}
