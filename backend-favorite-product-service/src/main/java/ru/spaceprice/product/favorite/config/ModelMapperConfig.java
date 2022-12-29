package ru.spaceprice.product.favorite.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.collection.Product;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<ProductDto, Product> propertyMapper = mapper.createTypeMap(ProductDto.class, Product.class);
        propertyMapper.addMappings(m -> m.skip(Product::setId));
        return mapper;
    }
}
