package taxi.taxi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.OrderResponseDto;
import taxi.taxi.mapper.OrderMapper;
import taxi.taxi.model.Driver;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.OrderRepository;

import java.util.ArrayList;

@Service
public class StatusService {


    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;

    public StatusService(OrderRepository orderRepository, DriverRepository driverRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
    }

    private Driver getDriver(long id){
        if(driverRepository.existsById(id)){
            return driverRepository.findById(id);
        }else {
            return null;
        }
    }

    public ResponseEntity<ArrayList<OrderResponseDto>> getStatus(User currentUser, int startIndex, int endIndex) {


        ArrayList<Order> orderList = orderRepository.findOrdersByStatus(currentUser.getId(),startIndex,endIndex-startIndex+1);

        ArrayList<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        orderList.forEach(order -> {orderResponseDtos.add(OrderMapper.orderMapperToOrderResponseDto(order,getDriver(order.getDriverId())));}
        );

        System.out.println(orderResponseDtos);

        return ResponseEntity.ok(orderResponseDtos);

    }


    public ResponseEntity<Object> getLastStatus(User currentUser) {

        ArrayList<Order> orderList = orderRepository.findOrdersByStatus(currentUser.getId(),0,1);

        ArrayList<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        orderList.forEach(order -> {orderResponseDtos.add(OrderMapper.orderMapperToOrderResponseDto(order,getDriver(order.getDriverId())));}
        );

        return ResponseEntity.ok(orderResponseDtos.get(0));
    }
}
