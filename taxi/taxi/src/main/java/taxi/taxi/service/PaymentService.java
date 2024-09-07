package taxi.taxi.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.ReportDto;
import taxi.taxi.model.Driver;
import taxi.taxi.model.Order;
import taxi.taxi.model.User;
import taxi.taxi.repository.DriverRepository;
import taxi.taxi.repository.OrderRepository;
import taxi.taxi.repository.UserRepository;

@Service
public class PaymentService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final DriverRepository driverRepository;

    public PaymentService(OrderRepository orderRepository, UserRepository userRepository, RabbitMQProducer rabbitMQProducer, DriverRepository driverRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.rabbitMQProducer = rabbitMQProducer;
        this.driverRepository = driverRepository;
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
            Driver driver = driverRepository.findById(order.getDriverId()).get();
            driver.setStatus(false);
            driverRepository.save(driver);

            rabbitMQProducer.sendReport(new ReportDto("payment","order="+order.getId()).toString());
            rabbitMQProducer.sendMessage("driver ba driverId "+order.getDriverId()+" safar khod ra tamam kard");


            return ResponseEntity.ok("ba movafaghiyat pardakht shod.");
        }else{

            rabbitMQProducer.sendReport(new ReportDto("miss payment","order="+order.getId()).toString());
            return ResponseEntity.ok("lotfan kif pool khod ra sharzh konid.");
        }
    }


    public ResponseEntity<String> charge(User currentUser, int money) {

        if(money < 0 || money > 1000){
            return ResponseEntity.badRequest().body("Invalid money");
        }

        currentUser.setBalance(currentUser.getBalance() + money);
        userRepository.save(currentUser);


        rabbitMQProducer.sendReport(new ReportDto("charging there wallet","userId="+currentUser.getId()).toString());

        return ResponseEntity.ok("sharzh ba movafaghiyat anjam shod.\nkif pool shoma darhale hazer " + currentUser.getBalance() + " tooman sharzh darad.");
    }
}
