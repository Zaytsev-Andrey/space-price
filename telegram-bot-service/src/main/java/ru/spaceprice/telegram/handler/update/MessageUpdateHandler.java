package ru.spaceprice.telegram.handler.update;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.spaceprice.message.property.AmqpExchangeProperty;
import ru.spaceprice.message.property.AmqpRoutingKeyProperty;

@Component
public class MessageUpdateHandler extends UpdateHandler {

    @Autowired
    protected MessageUpdateHandler(AmqpExchangeProperty exchangeProperty,
                                   AmqpRoutingKeyProperty routingKeyProperty,
                                   RabbitTemplate rabbitTemplate) {
        super(exchangeProperty.getDirectTelegramExchange(), routingKeyProperty.getMessageRoutingKey(), rabbitTemplate);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && !update.getMessage().isCommand();
    }

}
