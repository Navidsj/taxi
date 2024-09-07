package taxi.taxi.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

import java.util.concurrent.TimeUnit;

@RestController
public class PaymentController {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PaymentService paymentService;

    public PaymentController(UserRepository userRepository, JwtService jwtService, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payment( @RequestHeader("Authorization") String header) throws InterruptedException {

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();


        RedissonClient redissonClient = Redisson.create();

        RLock payLock = redissonClient.getLock("lock");

        boolean locked = payLock.tryLock(5,15, TimeUnit.SECONDS);

        if(locked) {

            return paymentService.pay(currentUser);
        }else{
            return ResponseEntity.ok("try another time");
        }
    }

}
