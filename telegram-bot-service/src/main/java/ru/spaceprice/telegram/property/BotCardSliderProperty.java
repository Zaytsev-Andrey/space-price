package ru.spaceprice.telegram.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(value = "bot.card-slider")
@PropertySource(value = "classpath:telegram.yaml", factory = BotYamlPropertySourceFactory.class)
public class BotCardSliderProperty {

    @Min(1)
    private int capacity;

    @Positive
    private int loadLimit;
}
