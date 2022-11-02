package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;
import ru.spaceprice.telegram.chat.slider.CardSliderNavigator;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.storage.repository.ProductCardSliderRepository;

@Service
@RequiredArgsConstructor
public class TelegramCallbackServiceImpl implements TelegramCallbackService {

    private final ProductCardSliderRepository productCardSliderRepository;

    @Override
    public void callbackRequest(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getFrom().getId().toString();
        ProductCardSlider productCardSlider = productCardSliderRepository.findById(chatId)
                .orElseThrow(IllegalArgumentException::new);
        CardSliderNavigator cardSliderNavigator = productCardSlider.navigator();

        switch (CardButtonCommand.valueOf(callbackQuery.getData())) {
            case NEXT:
                cardSliderNavigator.next();
                break;
            case PREVIOUS:
                cardSliderNavigator.previous();
                break;
        }
        productCardSliderRepository.save(productCardSlider);
    }

}
