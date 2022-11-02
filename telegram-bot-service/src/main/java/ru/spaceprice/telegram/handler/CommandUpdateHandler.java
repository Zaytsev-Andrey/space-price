package ru.spaceprice.telegram.handler;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.spaceprice.message.property.AmqpExchangeProperty;
import ru.spaceprice.message.property.AmqpRoutingKeyProperty;

@Component
public class CommandUpdateHandler extends UpdateHandler {

    @Autowired
    protected CommandUpdateHandler(AmqpExchangeProperty exchangeProperty,
                                   AmqpRoutingKeyProperty routingKeyProperty,
                                   RabbitTemplate rabbitTemplate) {
        super(exchangeProperty.getDirectTelegramExchange(), routingKeyProperty.getCommandRoutingKey(), rabbitTemplate);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }


}
