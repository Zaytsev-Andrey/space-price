package ru.spaceprice.telegram.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Log4j2
@RequiredArgsConstructor
public abstract class UpdateHandler {

    private final String exchange;

    private final String routingKey;

    private final AmqpTemplate amqpTemplate;

    public abstract boolean canHandle(Update update);

    public void handle(Update update) {
        log.info("Update sent with '{}' routing key", getRoutingKey());
        getAmqpTemplate().convertAndSend(
                getExchange(),
                getRoutingKey(),
                update.getMessage()
        );
    }
}
