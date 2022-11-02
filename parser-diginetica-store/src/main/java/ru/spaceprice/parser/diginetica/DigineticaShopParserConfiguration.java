package ru.spaceprice.parser.diginetica;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.spaceprice.parser.diginetica.converter.ConverterCitilinkDtoToDto;
import ru.spaceprice.parser.diginetica.converter.ConverterDigineticaDtoToDto;
import ru.spaceprice.parser.diginetica.converter.ConverterDto;
import ru.spaceprice.parser.diginetica.property.DigineticaConnectionProperty;

@ComponentScan
@Configuration
public class DigineticaShopParserConfiguration {

    @Bean
    public ConverterDto converterOldiDtoToDto(DigineticaConnectionProperty oldiConnectionProperty) {
        return new ConverterDigineticaDtoToDto(oldiConnectionProperty);
    }

    @Bean
    public ConverterDto converterCitilinkDtoToDto(DigineticaConnectionProperty citilinkConnectionProperty) {
        return new ConverterCitilinkDtoToDto(citilinkConnectionProperty);
    }

    @Bean
    public DigineticaParser oldiParser(DigineticaConnectionProperty oldiConnectionProperty) {
        return new DigineticaParser(oldiConnectionProperty, converterOldiDtoToDto(oldiConnectionProperty));
    }

    @Bean
    public DigineticaParser citilinkParser(DigineticaConnectionProperty citilinkConnectionProperty) {
        return new DigineticaParser(citilinkConnectionProperty, converterCitilinkDtoToDto(citilinkConnectionProperty));
    }
}
