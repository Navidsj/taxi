package taxi.taxi.service.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReporterConsumer {


    private final ObjectMapper jacksonObjectMapper;

    public RabbitMQReporterConsumer(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @RabbitListener(queues = "${rabbitmq.reporter.queue.name}")
    public void receive(String message) {
        System.out.println("report:\n"+message);
    }

}