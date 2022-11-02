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
@ConfigurationProperties(value = "bot.connection.search-product-service")
@PropertySource(value = "classpath:telegram.yaml", factory = BotYamlPropertySourceFactory.class)
public class SearchProductServiceConnectionProperty {

    @NotBlank
    private String uri;

    @NotBlank
    private String path;

    @NotBlank
    private String paramName;
}
