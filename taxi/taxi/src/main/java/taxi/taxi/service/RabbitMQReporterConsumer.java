package taxi.taxi.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import taxi.taxi.dto.ReportDto;

@Service
public class RabbitMQReporterConsumer {


    @RabbitListener(queues = "${rabbitmq.reporter.queue.name}")
    public void receive(String message) {
        System.out.println("report:\n"+message);
    }

}