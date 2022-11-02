package ru.spaceprice.telegram.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.redis.core.RedisHash;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.chat.slider.CardSliderLive;
import ru.spaceprice.telegram.chat.slider.CardSliderNavigation;
import ru.spaceprice.telegram.chat.slider.CardSliderNavigator;
import ru.spaceprice.telegram.chat.slider.Navigable;

import java.util.ArrayList;
import java.util.List;

@RedisHash("ProductCardSlider")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardSlider extends AbstractAggregateRoot<ProductCardSlider> implements Navigable {

    // chatId
    private String id;

    private Integer messageId;

    private List<ProductDto> products = new ArrayList<>();

    private int currentPosition;

    private boolean active = true;

    private ProductCardSlider(String id) {
        this.id = id;
    }

    public static ProductCardSlider newProductCardSlider(String chatId) {
        return new ProductCardSlider(chatId);
    }

    public void addProduct(ProductDto productDto) {
        products.add(productDto);
        registerEvent(this);
    }

    public void disable() {
        active = false;
        registerEvent(CardSliderLive.fromProductCardSlider(this));
        registerEvent(this);
    }

    private void incrementCurrentPosition() {
        currentPosition++;
        registerEvent(this);
        registerEvent(CardSliderNavigation.fromProductCardSlider(this));
    }

    private void decrementCurrentPosition() {
        currentPosition--;
        registerEvent(this);
    }

    @Override
    public CardSliderNavigator navigator() {
        return new CardSliderNavigator() {
            @Override
            public void next() {
                if (hasNext()) {
                    incrementCurrentPosition();
                }
            }

            @Override
            public void previous() {
                if (hasPrevious()) {
                    decrementCurrentPosition();
                }
            }

            @Override
            public boolean hasNext() {
                return currentPosition < size() - 1;
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition > 0;
            }

            @Override
            public int size() {
                return products.size();
            }

            @Override
            public ProductDto getCurrentProduct() {
                return products.get(currentPosition);
            }


        };
    }
}
