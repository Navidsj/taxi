package taxi.taxi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.*;
import org.springframework.data.redis.serializer.JacksonObjectReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import taxi.taxi.dto.OrderDto;
import taxi.taxi.dto.RegisterUserDto;
import taxi.taxi.model.Driver;
import taxi.taxi.model.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.UserRepository;
import taxi.taxi.service.JwtService;
import taxi.taxi.service.OrderService;

@RestController
public class OrderController {

    private final DriverRepository driverRepository;
    UserRepository userRepository;
    OrderService orderService;
    ObjectMapper jacksonObjectMapper;
    JwtService jwtService;

    public OrderController(OrderService orderService, ObjectMapper jacksonObjectMapper, UserRepository userRepository, JwtService jwtService, DriverRepository driverRepository) {
        this.orderService = orderService;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.driverRepository = driverRepository;
    }



    @PostMapping("/new/order")
    public ResponseEntity<String> newOrder(@RequestBody String body, @RequestHeader("Authorization") String header) throws JsonProcessingException {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Driver driver = new Driver();
        driver.setName("Driver");
        driver.setLocation(geometryFactory.createGeometry(geometryFactory.createPoint(new Coordinate(36.22,36.22))));
        System.out.println(driver.getLocation());

//        driverRepository.save(driver);

        driverRepository.findByDistinc((Point) driver.getLocation());

        String token = header.substring(7);
        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token)).get();

        OrderDto orderDto = jacksonObjectMapper.readValue(body,OrderDto.class);
        orderDto.setUserId(currentUser.getId());
        String responseBody = orderService.newOrder(orderDto);

        return ResponseEntity.ok(responseBody);
    }


}
