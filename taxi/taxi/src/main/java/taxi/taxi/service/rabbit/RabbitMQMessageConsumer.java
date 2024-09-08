package taxi.taxi.service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMessageConsumer {


    @RabbitListener(queues = "${rabbitmq.message.queue.name}")
    public void receive(String message) {
        System.out.println("message:\n"+message);
    }

}