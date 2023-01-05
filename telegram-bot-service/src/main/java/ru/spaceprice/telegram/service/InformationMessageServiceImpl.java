package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spaceprice.telegram.property.BotChatFormatProperty;
import ru.spaceprice.telegram.sender.BotSender;

@Service
@RequiredArgsConstructor
public class InformationMessageServiceImpl implements InformationMessageService {

    private final BotChatFormatProperty botChatFormatProperty;

    private final BotSender botSender;

    @Override
    public void sendGreetingsMessage(String chatId) {
        botSender.sendInformationMessage(chatId, botChatFormatProperty.getGreetings());
    }

    @Override
    public void sendEmptySearchMessage(String chatId) {
        botSender.sendInformationMessage(chatId, botChatFormatProperty.getSearchEmpty());
    }

}
