package ru.spaceprice.parser.diginetica.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaProductDto;
import ru.spaceprice.parser.diginetica.property.DigineticaConnectionProperty;

/**
 * Performs conversions between dto objects of the online store and ProductDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConverterDigineticaDtoToDto implements ConverterDto {

    private String shopName;

    private String shopUri;

    public ConverterDigineticaDtoToDto(DigineticaConnectionProperty connectionProperties) {
        this.shopName = connectionProperties.getShopName();
        this.shopUri = connectionProperties.getShopUri();
    }

    /**
     * Converts the dto object of the online store to ProductDto.
     *
     * @param digineticaProductDto - dto object of the online store.
     * @return - ProductDto object.
     */
    @Override
    public ProductDto convertToDto(DigineticaProductDto digineticaProductDto) {
        return new ProductDto(
                convertId(digineticaProductDto),
                this.shopName,
                convertName(digineticaProductDto),
                convertPrice(digineticaProductDto),
                null,
                convertShopUri(digineticaProductDto),
                convertImageUri(digineticaProductDto)
        );
    }

    protected String convertId(DigineticaProductDto digineticaProductDto) {
        return digineticaProductDto.getId();
    }

    protected String convertName(DigineticaProductDto digineticaProductDto) {
        return digineticaProductDto.getName();
    }

    protected String convertPrice(DigineticaProductDto digineticaProductDto) {
        return digineticaProductDto.getPrice();
    }

    protected String convertShopUri(DigineticaProductDto digineticaProductDto) {
        return this.shopUri + digineticaProductDto.getLink_url();
    }

    protected String convertImageUri(DigineticaProductDto digineticaProductDto) {
        return digineticaProductDto.getImage_url();
    }
}
