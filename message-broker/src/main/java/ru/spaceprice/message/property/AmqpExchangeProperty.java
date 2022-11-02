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
@ConfigurationProperties(value = "amqp.exchange")
public class AmqpExchangeProperty {

    private String directTelegramExchange;

}
