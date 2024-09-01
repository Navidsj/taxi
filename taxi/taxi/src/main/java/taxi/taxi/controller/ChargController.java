package taxi.taxi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.model.User;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.JwtService;

@RestController
public class ChargController {


    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ChargController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/charg/balance")
    public ResponseEntity<String> payment(@RequestBody String body,@RequestHeader("Authorization") String header){

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

        currentUser.setBalance(currentUser.getBalance() + Integer.parseInt(body));
        userRepository.save(currentUser);

        return ResponseEntity.ok("sharzh ba movafaghiyat anjam shod.\nkif pool shoma darhale hazer " + currentUser.getBalance() + " tooman sharzh darad.");
    }

}
