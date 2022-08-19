package ru.spaceprice.parser.diginetica.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Parameters for connecting to the Citilink store.
 */
@Component
@PropertySource("classpath:citilink-connection.properties")
@ConfigurationProperties(value="shop.citilink")
public class CitilinkConnectionProperty extends DigineticaConnectionProperty {
}
