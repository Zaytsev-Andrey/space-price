package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.telegram.handler.command.CommandHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramCommandServiceImpl implements TelegramCommandService {

    private final List<CommandHandler> handlers;

    @Override
    public void commandRequest(Message message) {
        String messageCommand = message.getText();

        handlers.stream()
                .filter(commandHandler -> commandHandler.canHandle(messageCommand))
                .findFirst()
                .ifPresent(commandHandler -> commandHandler.handle(message));
    }
}
