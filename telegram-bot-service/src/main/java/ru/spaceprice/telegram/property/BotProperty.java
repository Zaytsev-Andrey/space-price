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
@ConfigurationProperties(value = "bot.authorization")
@PropertySource(value = "classpath:telegram.yaml", factory = BotYamlPropertySourceFactory.class)
public class BotProperty {

    @NotBlank
    private String username;

    @NotBlank
    private String token;
}
