package ru.spaceprice.telegram.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(value = "bot.chat-format")
@PropertySource(value = "classpath:messages.yaml", factory = BotYamlPropertySourceFactory.class)
public class BotChatFormatProperty {

    @NotBlank
    private String buttonNext;

    @NotBlank
    private String buttonPrevious;

    @NotBlank
    private String buttonPage;

    @NotBlank
    private String addFavorites;

    @NotBlank
    private String removeFavorites;

    @NotBlank
    private String title;

    @NotBlank
    private String price;

    @NotBlank
    private String shopLink;

    @NotBlank
    private String oldPrice;

    @NotBlank
    private String priceUp;

    @NotBlank
    private String priceDown;

    @NotBlank
    private String greetings;

    @NotBlank
    private String favoritesEmpty;
}
