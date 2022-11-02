package ru.spaceprice.message.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PropertySource(value = "classpath:amqp.yaml", factory = MessageBrokerYamlPropertySourceFactory.class)
@ConfigurationProperties(value = "amqp.routing-key")
public class AmqpRoutingKeyProperty {

    private String productRoutingKey;

    private String callbackRoutingKey;

    private String messageRoutingKey;

    private String commandRoutingKey;

    private String notifyRoutingKey;

}
