package taxi.taxi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.model.User;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.JwtService;
import taxi.taxi.service.PaymentService;
import taxi.taxi.service.StatusService;

@RestController
public class StatusController {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final StatusService statusService;

    public StatusController(UserRepository userRepository, JwtService jwtService, StatusService statusService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.statusService = statusService;
    }

    @GetMapping("/orders/status")
    public ResponseEntity<String> getStatus(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

        return statusService.getStatus(currentUser);
    }





}
