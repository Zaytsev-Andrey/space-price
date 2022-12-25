package ru.spaceprice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productShopId;

    private String shopName;

    private String name;

    private String price;

    private String oldPrice;

    private String productLink;

    private String imageLink;

}
