package taxi.taxi.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taxi.taxi.model.User;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.JwtService;
import taxi.taxi.service.PaymentService;

import java.util.concurrent.TimeUnit;

@RestController
public class ChargeController{


    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public ChargeController(JwtService jwtService, UserRepository userRepository, PaymentService paymentService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @PutMapping("/charge/balance")
    public ResponseEntity<String> payment(@RequestParam int money, @RequestHeader("Authorization") String header) throws InterruptedException {

        RedissonClient redissonClient = Redisson.create();

        RLock lock = redissonClient.getLock("lock");

        boolean locked = lock.tryLock(5,10, TimeUnit.SECONDS);

        if(locked) {

            String token = header.substring(7);
            User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

            return paymentService.charge(currentUser,money);
        }else{
            return ResponseEntity.ok("Try another time please");

        }

    }

}
