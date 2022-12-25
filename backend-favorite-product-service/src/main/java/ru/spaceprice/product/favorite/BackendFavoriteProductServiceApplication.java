package ru.spaceprice.product.favorite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class BackendFavoriteProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendFavoriteProductServiceApplication.class, args);
    }

}
