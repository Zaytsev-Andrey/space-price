package ru.spaceprice.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramCommandService {

    void commandRequest(Message message);
}
