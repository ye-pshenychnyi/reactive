package ua.yevhenii.reactive.q;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.ConnectionFactory;

/**
 * @author ext.epshenichniy
 */
@Configuration
public class QueueConfiguration {

    public static final String QUEUE_NAME = "reactiveQ";

    private final ConnectionFactory connectionFactory;

    public QueueConfiguration(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    @Transactional
    public Publisher<Message<String>> jmsReactiveSource() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.connectionFactory)
                        .destination(QUEUE_NAME))
                .channel(MessageChannels.queue())
                .log()
                .toReactivePublisher();
    }
}
