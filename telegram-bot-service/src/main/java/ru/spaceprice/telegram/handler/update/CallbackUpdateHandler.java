package ru.spaceprice.telegram.handler.update;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.spaceprice.message.property.AmqpExchangeProperty;
import ru.spaceprice.message.property.AmqpRoutingKeyProperty;

@Component
@Log4j2
public class CallbackUpdateHandler extends UpdateHandler {

    protected CallbackUpdateHandler(AmqpExchangeProperty exchangeProperty,
                                    AmqpRoutingKeyProperty routingKeyProperty,
                                    RabbitTemplate rabbitTemplate) {
        super(exchangeProperty.getDirectTelegramExchange(), routingKeyProperty.getCallbackRoutingKey(), rabbitTemplate);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        log.info("Update sent with '{}' routing key", getRoutingKey());
        getAmqpTemplate().convertAndSend(
                getExchange(),
                getRoutingKey(),
                update.getCallbackQuery()
        );
    }
}
