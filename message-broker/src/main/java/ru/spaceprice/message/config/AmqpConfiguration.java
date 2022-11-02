package ru.spaceprice.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.spaceprice.message.property.AmqpExchangeProperty;
import ru.spaceprice.message.property.AmqpQueueProperty;
import ru.spaceprice.message.property.AmqpRoutingKeyProperty;

@Configuration
@PropertySource(value = "classpath:amqp.yaml")
public class AmqpConfiguration {

    private final AmqpExchangeProperty exchangeProperty;

    private final AmqpQueueProperty queueProperty;

    private final AmqpRoutingKeyProperty routingKeyProperty;

    @Autowired
    public AmqpConfiguration(AmqpExchangeProperty exchangeProperty,
                             AmqpQueueProperty queueProperty,
                             AmqpRoutingKeyProperty routingKeyProperty) {
        this.exchangeProperty = exchangeProperty;
        this.queueProperty = queueProperty;
        this.routingKeyProperty = routingKeyProperty;
    }

    @Bean
    public DirectExchange telegramExchange() {
        return new DirectExchange(exchangeProperty.getDirectTelegramExchange());
    }

    @Bean
    public Queue productTelegramQueue() {
        return new Queue(queueProperty.getProductQueue(), true);
    }

    @Bean
    public Queue callbackTelegramQueue() {
        return new Queue(queueProperty.getCallbackQueue(), false);
    }

    @Bean
    public Queue messageTelegramQueue() {
        return new Queue(queueProperty.getMessageQueue(), true);
    }

    @Bean
    public Queue commandTelegramQueue() {
        return new Queue(queueProperty.getCommandQueue(), true);
    }

    @Bean
    public Queue notifyTelegramQueue() {
        return new Queue(queueProperty.getNotifyQueue(), true);
    }

    @Bean
    public Binding productTelegramBinding() {
        return BindingBuilder
                .bind(productTelegramQueue())
                .to(telegramExchange())
                .with(routingKeyProperty.getProductRoutingKey());
    }

    @Bean
    public Binding callbackTelegramBinding() {
        return BindingBuilder
                .bind(callbackTelegramQueue())
                .to(telegramExchange())
                .with(routingKeyProperty.getCallbackRoutingKey());
    }

    @Bean
    public Binding messageTelegramBinding() {
        return BindingBuilder
                .bind(messageTelegramQueue())
                .to(telegramExchange())
                .with(routingKeyProperty.getMessageRoutingKey());
    }

    @Bean
    public Binding commandTelegramBinding() {
        return BindingBuilder
                .bind(commandTelegramQueue())
                .to(telegramExchange())
                .with(routingKeyProperty.getCommandRoutingKey());
    }

    @Bean
    public Binding notifyTelegramBinding() {
        return BindingBuilder
                .bind(notifyTelegramQueue())
                .to(telegramExchange())
                .with(routingKeyProperty.getNotifyRoutingKey());
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
