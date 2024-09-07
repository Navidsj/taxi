package taxi.taxi.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.OrderRepository;
import taxi.taxi.repository.UserRepository;

@Service
public class PaymentService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> pay(User currentUser){

        if(currentUser.getOrderId() == 0) {
            return ResponseEntity.ok("shoma darhal hazer safari nadarid.");
        }
        Order order = orderRepository.findById(currentUser.getOrderId()).get();

        if(currentUser.getBalance() >= order.getPrice()){
            currentUser.setBalance(currentUser.getBalance() - order.getPrice());
            currentUser.setOrderId(0);
            userRepository.save(currentUser);
            order.setStatus("payed");
            orderRepository.save(order);
            return ResponseEntity.ok("ba movafaghiyat pardakht shod.");
        }else{
            return ResponseEntity.ok("lotfan kif pool khod ra sharzh konid.");
        }
    }


    public ResponseEntity<String> charge(User currentUser, int money) {

        if(money < 0 || money > 1000){
            return ResponseEntity.badRequest().body("Invalid money");
        }

        currentUser.setBalance(currentUser.getBalance() + money);
        userRepository.save(currentUser);

        return ResponseEntity.ok("sharzh ba movafaghiyat anjam shod.\nkif pool shoma darhale hazer " + currentUser.getBalance() + " tooman sharzh darad.");
    }
}
