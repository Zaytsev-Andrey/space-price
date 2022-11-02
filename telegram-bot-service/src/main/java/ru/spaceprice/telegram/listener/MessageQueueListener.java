package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.telegram.service.TelegramMessageService;


@Component
@Log4j2
@PropertySource(value = "classpath:amqp.yaml")
@RequiredArgsConstructor
public class MessageQueueListener {

    private final TelegramMessageService telegramMessageService;

    @RabbitListener(queues = "${amqp.queue.message-queue}")
    public void listenMessageTelegram(Message message) {
        log.info("Message listener received: {}", message.getText());
        telegramMessageService.messageRequest(message);
    }

}
