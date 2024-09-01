package taxi.taxi.service;


import org.aspectj.weaver.ast.Or;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.OrderDto;
import taxi.taxi.mapper.OrderMapper;
import taxi.taxi.model.Driver;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.OrderRepository;
import taxi.taxi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;

@Service
public class OrderService {


    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, DriverRepository driverRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    public String newOrder(OrderDto orderDto) {

        Order order = OrderMapper.mapper(orderDto);
        orderRepository.save(order);

        ArrayList<Driver> arrayList = driverRepository.findByDistinc(order.getStartLocation(),"mashin");

        if (arrayList.isEmpty()) {
            order.setStatus("driver not found");
            return "ranande peyda nashod!";
        } else {

            Driver driver = arrayList.get(0);
            driver.setStatus(true);
            driverRepository.save(driver);

            User user = userRepository.findById(order.getUserId()).get();
            user.setOrderId(order.getId());
            userRepository.save(user);

            return driver.getName() +" safar shoma ra ghabol kard.";
        }
    }

}
