package taxi.taxi.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taxi.taxi.checker.ValueCheck;
import taxi.taxi.dto.LoginResponseDto;
import taxi.taxi.dto.LoginUserDto;
import taxi.taxi.dto.RegisterUserDto;
import taxi.taxi.dto.ReportDto;
import taxi.taxi.model.User;
import taxi.taxi.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RabbitMQProducer rabbitMQProducer;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RabbitMQProducer rabbitMQProducer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public ResponseEntity<String> signup(RegisterUserDto input){
        if(userRepository.findByEmail(input.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("In email ghablan estefade shode ast!!!");
        }

        ResponseEntity<String> res = ValueCheck.checkUser(input.getName(),input.getEmail(),input.getPassword());

        if(res != null) return res;

        User user = new User();
        user.setName(input.getName());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());

        userRepository.save(user);
        rabbitMQProducer.sendReport(new ReportDto("user added","userId="+user.getId()).toString());

        return ResponseEntity.ok(user.getName() + " jan account shoma sakhte shod.");
    }

    public ResponseEntity<LoginResponseDto> authenticate(LoginUserDto input){

        if(userRepository.findByEmail(input.getEmail()).isPresent() && passwordEncoder.matches(input.getPassword(), userRepository.findByEmail(input.getEmail()).get().getPassword())) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
            User authonticatedUser = userRepository.findByEmail(input.getEmail()).orElseThrow();

            String jwtToken = jwtService.generateToken(authonticatedUser);

            LoginResponseDto loginResponse = new LoginResponseDto(jwtToken,jwtService.getJwtExpiration());

            return ResponseEntity.ok(loginResponse);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new LoginResponseDto("Email or Password not correct!",0));
        }

    }


}
