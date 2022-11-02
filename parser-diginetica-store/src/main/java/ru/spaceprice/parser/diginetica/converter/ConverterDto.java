package ru.spaceprice.parser.diginetica.converter;

import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaProductDto;

public interface ConverterDto {

    ProductDto convertToDto(DigineticaProductDto digineticaProductDto);
}
