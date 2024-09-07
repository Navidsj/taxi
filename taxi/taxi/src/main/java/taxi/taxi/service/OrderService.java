package taxi.taxi.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.OrderDto;
import taxi.taxi.dto.ReportDto;
import taxi.taxi.mapper.CoordinateMapper;
import taxi.taxi.mapper.OrderMapper;
import taxi.taxi.model.Driver;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.OrderRepository;
import taxi.taxi.repository.UserRepository;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.round;

@Service
public class OrderService {


    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final StatusService statusService;
    private final RabbitMQProducer rabbitMQProducer;
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, DriverRepository driverRepository, UserRepository userRepository, StatusService statusService, RabbitMQProducer rabbitMQProducer) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.statusService = statusService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public ResponseEntity<Object> newOrder(User currentUser, OrderDto orderDto) {


        if(currentUser.getOrderId() != 0)
            return ResponseEntity.ok("ta safar akhar ra pardakht nakonid emkan safar jadid nadarid.");

        if(GetVehicles.getVehiclePrice(orderDto.getVehicleType()) == -1){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("vehicle not correct!");
        }

        Order order = OrderMapper.orderDtoMapperToOrder(orderDto);
        order.setUserId(currentUser.getId());

        order.setPrice((int) max(10,CoordinateMapper
                .haversine(orderDto
                        .getStartLat(),orderDto
                        .getStartLong(),orderDto
                        .getEndLat(),orderDto
                        .getEndLong())*GetVehicles.getVehiclePrice(orderDto.getVehicleType())));

        orderRepository.save(order);

        ArrayList<Driver> driverArrayList = driverRepository.findByDistinc(order.getStartLocation(),order.getVehicleType());

        if (driverArrayList.isEmpty()) {
            order.setStatus("driver not found");
            orderRepository.save(order);
            rabbitMQProducer.sendReport(new ReportDto("miss Order","orderId="+order.getId()).toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("driver not found!");
        } else {

            double minDistance = Double.MAX_VALUE;
            Driver driver = driverArrayList.get(0);
            for (Driver d : driverArrayList) {
                if(minDistance > d.getLocation().distance(order.getStartLocation())) {
                    minDistance = d.getLocation().distance(order.getStartLocation());
                    driver = d;
                }
            }

            driver.setStatus(true);
            driverRepository.save(driver);

            order.setDriverId(driver.getId());
            orderRepository.save(order);

            User user = userRepository.findById(order.getUserId()).get();
            user.setOrderId(order.getId());
            userRepository.save(user);

            rabbitMQProducer.sendMessage("driver ba driverId "+order.getDriverId()+" order ra ba orderId "+order.getId()+" az user ba userId "+user.getId()+" ghabool kard.");

            rabbitMQProducer.sendReport(new ReportDto("new Order","orderId="+order.getId()).toString());

            return statusService.getLastStatus(currentUser);
        }
    }

}
