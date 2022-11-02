package ru.spaceprice.telegram.service;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface TelegramCallbackService {

    void callbackRequest(CallbackQuery callbackQuery);
}
