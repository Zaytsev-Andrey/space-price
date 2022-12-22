package ru.spaceprice.database.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field
    @DBRef
    private Set<Product> products = new HashSet<>();
}
