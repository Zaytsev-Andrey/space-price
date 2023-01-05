package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.telegram.chat.command.BotCommand;
import ru.spaceprice.telegram.service.TelegramCommandService;


@Component
@RequiredArgsConstructor
@Log4j2
@PropertySource(value = "classpath:amqp.yaml")
public class CommandQueueListener {

    private final TelegramCommandService telegramCommandService;

    @RabbitListener(queues = "${amqp.queue.command-queue}")
    public void listenCommandTelegram(Message message) {
        log.info("Command listener received: '{}'", message.getText());
        telegramCommandService.commandRequest(message);
    }

}
