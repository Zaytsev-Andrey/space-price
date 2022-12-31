package ru.spaceprice.product.favorite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RefreshScope
@EnableSwagger2
public class BackendFavoriteProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendFavoriteProductServiceApplication.class, args);
    }

}
