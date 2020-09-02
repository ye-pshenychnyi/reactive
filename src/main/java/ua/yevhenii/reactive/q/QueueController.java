package ua.yevhenii.reactive.q;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static ua.yevhenii.reactive.q.QueueConfiguration.QUEUE_NAME;

/**
 * @author ext.epshenichniy
 */
@RestController
public class QueueController {

    private final Publisher<Message<String>> jmsReactiveSource;
    private final JmsTemplate jmsTemplate;

    public QueueController(Publisher<Message<String>> jmsReactiveSource, JmsTemplate jmsTemplate) {
        this.jmsReactiveSource = jmsReactiveSource;
        this.jmsTemplate = jmsTemplate;
    }


    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getPatientAlerts() {
        return Flux.from(jmsReactiveSource)
                .map(Message::getPayload);
    }

    @GetMapping(value = "/generate")
    public void generateJmsMessage() {
        for (int i = 0; i < 100; i++) {
            this.jmsTemplate.convertAndSend(QUEUE_NAME, "testMessage #" + (i + 1));
        }
    }
}
