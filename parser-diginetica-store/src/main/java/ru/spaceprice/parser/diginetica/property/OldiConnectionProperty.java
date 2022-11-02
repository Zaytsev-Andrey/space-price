package ru.spaceprice.parser.diginetica.property;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Parameters for connecting to the Oldi store.
 */
@Component
@PropertySource(value = "classpath:oldi.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(value="shop.oldi")
public class OldiConnectionProperty extends DigineticaConnectionProperty {
}
