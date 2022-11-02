package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.spaceprice.telegram.service.TelegramCallbackService;

@Component
@PropertySource(value = "classpath:amqp.yaml")
@RequiredArgsConstructor
public class CallbackQueueListener {

    private final TelegramCallbackService telegramCallbackService;

    @RabbitListener(queues = "${amqp.queue.callback-queue}")
    public void listenCallbackTelegram(CallbackQuery callbackQuery) {
        telegramCallbackService.callbackRequest(callbackQuery);
    }
}
