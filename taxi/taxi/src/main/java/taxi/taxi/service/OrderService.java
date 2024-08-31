package taxi.taxi.service;


import org.aspectj.weaver.ast.Or;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.OrderDto;
import taxi.taxi.mapper.OrderMapper;
import taxi.taxi.model.Driver;
import taxi.taxi.model.Order;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;

@Service
public class OrderService {


    private final DriverRepository driverRepository;
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, DriverRepository driverRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
    }

    public String  newOrder(OrderDto orderDto) {

        Order order = OrderMapper.mapper(orderDto);
        orderRepository.save(order);

//        ArrayList<Driver> arrayList = driverRepository.findByDistinc(order.getStartLocation())  ;
//
//        System.out.println(arrayList.toString());

        return order.toString();
    }


}
