package ru.spaceprice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AbstractProductDto {

    private String name;

    private String price;

    private String oldPrice;

    private String productLink;

    private String imageLink;

    private String shopName;

}
