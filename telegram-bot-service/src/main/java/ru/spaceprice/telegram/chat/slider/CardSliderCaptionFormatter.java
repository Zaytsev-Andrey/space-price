package ru.spaceprice.telegram.chat.slider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.BotChatFormatProperty;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CardSliderCaptionFormatter implements CardSliderFormatter {

    private final BotChatFormatProperty chatFormatProperty;

    private String formatTitle(String title) {
        title = title.replaceAll("[<>]", "");
        return String.format(chatFormatProperty.getTitle(), title);
    }

    private String formatPrice(String price) {
        BigDecimal bigDecimalPrice = new BigDecimal(price);
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return String.format(chatFormatProperty.getPrice(), bigDecimalPrice);
    }

    private String formatShopLink(String productLink, String shopName) {
        return String.format(chatFormatProperty.getShopLink(), productLink, shopName);
    }

    @Override
    public String format(ProductCardSlider productCardSlider) {
        ProductDto productDto = productCardSlider.navigator().getCurrentProduct();
        return formatTitle(productDto.getName()) +
                formatPrice(productDto.getPrice()) +
                formatShopLink(productDto.getProductLink(), productDto.getShopName());
    }
}
