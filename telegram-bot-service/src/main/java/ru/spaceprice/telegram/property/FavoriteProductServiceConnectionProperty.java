package ru.spaceprice.telegram.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(value = "bot.connection.favorite-product-service")
public class FavoriteProductServiceConnectionProperty {

    @NotBlank
    private String host;

    @NotBlank
    private String service;

    @NotBlank
    private String basePath;

    @NotBlank
    private String path;

    public String getUri() {
        return new StringBuilder()
                .append(host)
                .append(service)
                .append(basePath)
                .toString();
    }
}
