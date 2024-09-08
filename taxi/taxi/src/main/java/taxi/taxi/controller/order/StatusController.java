package taxi.taxi.controller.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.dto.order.OrderResponseDto;
import taxi.taxi.model.users.User;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.authentication.JwtService;
import taxi.taxi.service.order.StatusService;

import java.util.ArrayList;

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
    public ResponseEntity<ArrayList<OrderResponseDto>> getStatus(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

        return statusService.getStatus(currentUser,0,10);
    }





}
