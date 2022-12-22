package ru.spaceprice.database.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
@CompoundIndex(def = "{'productShopId':1, 'shopName':1}", name = "uci_productShopId_shopName", unique = true)
public class Product {

    @Id
    private String id;

    @Field
    private String productShopId;

    @Field
    private String shopName;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal coast;

    @Field
    private String imageLink;

    @Field
    private String productLink;
}
