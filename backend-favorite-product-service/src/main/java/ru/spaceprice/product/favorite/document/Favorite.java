package ru.spaceprice.product.favorite.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "favorites")
public class Favorite {

    @Id
    private String id;

    @Field
    @Indexed(name = "ui_userId", unique = true)
    private String userId;

    @Field(targetType = FieldType.OBJECT_ID)
    private Set<String> productIds = new HashSet<>();

    public Favorite(String userId) {
        this.userId = userId;
    }

    public boolean addProduct(String product) {
        if (productIds == null) {
            productIds = new HashSet<>();
        }
        return productIds.add(product);
    }
}
