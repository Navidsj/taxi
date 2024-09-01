package taxi.taxi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.OrderRepository;

import java.util.ArrayList;

@Service
public class StatusService {


    private final OrderRepository orderRepository;

    public StatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<String> getStatus(User currentUser) {


        ArrayList<Order> arrayList = orderRepository.findOrdersByStatus(currentUser.getId());

        return ResponseEntity.ok(arrayList.toString());

    }






}
