package taxi.taxi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.OrderRepository;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.JwtService;
import taxi.taxi.service.PaymentService;

@RestController
public class PaymentController {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public PaymentController(UserRepository userRepository, JwtService jwtService, OrderRepository orderRepository, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payment( @RequestHeader("Authorization") String header){

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

        if(currentUser.getOrderId() == 0){
            return ResponseEntity.ok("shoma darhal hazer safari nadarid.");
        }else{
            return paymentService.pay(currentUser);
        }
    }





}
