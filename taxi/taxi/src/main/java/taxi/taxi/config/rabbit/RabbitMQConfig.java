package taxi.taxi.config.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.reporter.queue.name}")
    private String reporterQueueName;


    @Value("${rabbitmq.message.queue.name}")
    private String messageQueueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.reporter.routing.key.name}")
    private String reporterRoutingKey;

    @Value("${rabbitmq.message.routing.key.name}")
    private String messageRoutingKey;


    @Bean
    public Queue reportQueue(){
        return new Queue(reporterQueueName);
    }
    @Bean
    public Queue messageQueue(){
        return new Queue(messageQueueName);
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(reportQueue()).to(exchange()).with(reporterRoutingKey);
    }

    @Bean
    Binding binding2(){
        return BindingBuilder.bind(messageQueue()).to(exchange()).with(messageRoutingKey);
    }

}
