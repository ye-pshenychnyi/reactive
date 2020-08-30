package ua.yevhenii.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@RestController
public class ReactiveApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ConnectableFlux<Integer> publish = Flux
                .create(sink -> {
                    Scanner scanner = new Scanner(System.in);
                    while (scanner.hasNext()) {
                        sink.next(scanner.nextLine());
                    }
                })
                .buffer(Duration.ofMillis(1000))
                .map(List::size)
                .filter(size -> size > 2)
                .publish();

        publish.subscribe(System.out::println);
        publish.subscribe(o -> System.out.println(o.getClass()));
        publish.connect();
    }


    @Qualifier("jmsConnectionFactory")
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private JmsTemplate jmsTemplate;


    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public void receiveMessage(final Message jsonMessage) {

        System.out.println("Received message " + jsonMessage);
    }
}
