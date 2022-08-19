package ru.spaceprice.parser.diginetica.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Parameters for connecting to the Oldi store.
 */
@Component
@PropertySource("classpath:oldi-connection.properties")
@ConfigurationProperties(value="shop.oldi")
public class OldiConnectionProperty extends DigineticaConnectionProperty {
}
