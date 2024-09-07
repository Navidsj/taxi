package taxi.taxi.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.reporter.routing.key.name}")
    private String reporterRoutingKey;

    @Value("${rabbitmq.message.routing.key.name}")
    private String messageRoutingKey;


    RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReport(String message){
        rabbitTemplate.convertAndSend(exchangeName, reporterRoutingKey, message);
    }

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(exchangeName, messageRoutingKey, message);
    }

}
