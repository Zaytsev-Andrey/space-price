package ru.spaceprice.product.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.spaceprice.parser.Parser;
import ru.spaceprice.parser.diginetica.DigineticaShopParserConfiguration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Import(DigineticaShopParserConfiguration.class)
public class ParserConfig {

    private final List<Parser> parsers;

    @Autowired
    public ParserConfig(List<Parser> parsers) {
        this.parsers = parsers;
    }

    @Bean
    public Map<String, Parser> activeParserMap() {
        return parsers.stream()
                .collect(Collectors.toMap(Parser::getShopName, Function.identity()));
    }
}
