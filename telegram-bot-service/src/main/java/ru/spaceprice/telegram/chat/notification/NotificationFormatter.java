package ru.spaceprice.telegram.chat.notification;


import ru.spaceprice.dto.ProductDto;

public interface NotificationFormatter {

    String format(ProductDto productDto);
}
