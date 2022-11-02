package ru.spaceprice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto extends AbstractProductDto {

    private String name;

    private String price;

    private String oldPrice;

    private String productLink;

    private String imageLink;

    private String shopName;

    public ProductDto(String id, String name, String price, String productLink, String imageLink, String shopName) {
        super(id);
        this.name = name;
        this.price = price;
        this.productLink = productLink;
        this.imageLink = imageLink;
        this.shopName = shopName;
    }
}
