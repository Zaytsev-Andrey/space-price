package ru.spaceprice.telegram.handler.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;
import ru.spaceprice.telegram.service.ProductCardSliderService;

@Component
@RequiredArgsConstructor
public class PreviousCallbackHandler implements CallbackHandler {

    private final ProductCardSliderService productCardSliderService;

    @Override
    public boolean canHandle(CardButtonCommand command) {
        return CardButtonCommand.PREVIOUS.equals(command);
    }

    @Override
    public void handle(String chatId) {
        productCardSliderService.update(chatId, p -> p.navigator().previous());
    }
}
