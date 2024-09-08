package taxi.taxi.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.dto.order.OrderDto;
import taxi.taxi.model.users.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.authentication.JwtService;
import taxi.taxi.service.order.OrderService;
import taxi.taxi.service.rabbit.RabbitMQProducer;

@RestController
public class OrderController {

    private final DriverRepository driverRepository;
    private final RabbitMQProducer rabbitMQProducer;
    UserRepository userRepository;
    OrderService orderService;
    ObjectMapper jacksonObjectMapper;
    JwtService jwtService;

    public OrderController(OrderService orderService, ObjectMapper jacksonObjectMapper, UserRepository userRepository, JwtService jwtService, DriverRepository driverRepository, RabbitMQProducer rabbitMQProducer) {
        this.orderService = orderService;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.driverRepository = driverRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }



    @PostMapping("/new/order")
    public ResponseEntity<Object> newOrder(@RequestBody OrderDto orderDto, @RequestHeader("Authorization") String header) throws JsonProcessingException, BadRequestException {

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow(() -> new BadRequestException("User not found"));

        return orderService.newOrder(currentUser,orderDto);
    }




}
