package ru.spaceprice.telegram.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import ru.spaceprice.dto.ProductDto;

import java.util.HashSet;
import java.util.Set;

@RedisHash("FavoriteProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavorite {

    // chatId
    private String id;

    private Set<ProductDto> favoriteProducts = new HashSet<>();
}
