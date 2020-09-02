package ua.yevhenii.reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication

public class ReactiveApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        ConnectableFlux<Integer> publish = Flux
//                .create(sink -> {
//                    Scanner scanner = new Scanner(System.in);
//                    while (scanner.hasNext()) {
//                        sink.next(scanner.nextLine());
//                    }
//                })
//                .buffer(Duration.ofMillis(1000))
//                .map(List::size)
//                .filter(size -> size > 2)
//                .publish();
//
//        publish.subscribe(System.out::println);
//        publish.subscribe(o -> System.out.println(o.getClass()));
//        publish.connect();

//        Flux.from(jmsReactiveSource())
//                .map(Message::getPayload)
//                .subscribe(System.out::println);

        new Scanner(System.in).next();
    }

}
