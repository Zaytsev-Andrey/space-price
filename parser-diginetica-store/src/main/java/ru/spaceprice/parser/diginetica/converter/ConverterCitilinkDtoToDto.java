package ru.spaceprice.parser.diginetica.converter;

import ru.spaceprice.parser.diginetica.dto.DigineticaProductDto;
import ru.spaceprice.parser.diginetica.property.DigineticaConnectionProperty;

public class ConverterCitilinkDtoToDto extends ConverterDigineticaDtoToDto {

    private final String imageUri;

    public ConverterCitilinkDtoToDto(DigineticaConnectionProperty digineticaConnectionProperty) {
        super(digineticaConnectionProperty);
        this.imageUri = digineticaConnectionProperty.getImageUri();
    }

    @Override
    protected String convertImageUri(DigineticaProductDto digineticaProductDto) {
        String[] uri = digineticaProductDto.getImage_url().split("/");
        String imageName = uri[uri.length - 1];
        imageName = imageName.replace("_vb_", "_v01_");
        return imageUri + imageName;
    }
}
