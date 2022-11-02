package ru.spaceprice.telegram.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
@Log4j2
@PropertySource(value = "classpath:amqp.yaml")
public class CommandQueueListener {

    @RabbitListener(queues = "${amqp.queue.command-queue}")
    public void listenCommandTelegram(Message message) {
        log.info("Command listener received: {}", message.getText());
    }

}
