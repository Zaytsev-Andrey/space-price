package ru.spaceprice.telegram.chat.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.BotChatFormatProperty;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PriceNotificationFormatter implements NotificationFormatter {

    private final BotChatFormatProperty chatFormatProperty;

    private String formatTitle(String title) {
        title = title.replaceAll("[<>]", "");
        return String.format(chatFormatProperty.getTitle(), title);
    }

    private String formatOldPrice(String price) {
        BigDecimal bigDecimalPrice = new BigDecimal(price);
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return String.format(chatFormatProperty.getOldPrice(), bigDecimalPrice);
    }

    private String formatPrice(String price, String oldPrice) {
        BigDecimal bigDecimalPrice = new BigDecimal(price);
        BigDecimal bigDecimalOldPrice = new BigDecimal(oldPrice);
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String priceFormatted;
        if (bigDecimalPrice.compareTo(bigDecimalOldPrice) < 0) {
            priceFormatted =
                    String.format(chatFormatProperty.getPriceDown(), bigDecimalPrice);
        } else {
            priceFormatted =
                    String.format(chatFormatProperty.getPriceUp(), bigDecimalPrice);
        }
        return priceFormatted;
    }

    private String formatShopLink(String productLink, String shopName) {
        return String.format(chatFormatProperty.getShopLink(), productLink, shopName);
    }

    @Override
    public String format(ProductDto productDto) {
        return formatTitle(productDto.getName()) +
                formatOldPrice(productDto.getOldPrice()) +
                formatPrice(productDto.getPrice(), productDto.getOldPrice()) +
                formatShopLink(productDto.getProductLink(), productDto.getShopName());
    }
}
