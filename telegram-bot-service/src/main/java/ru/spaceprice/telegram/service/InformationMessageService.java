package ru.spaceprice.telegram.service;

public interface InformationMessageService {

    void sendGreetingsMessage(String chatId);

    void sendEmptySearchMessage(String chatId);

}
