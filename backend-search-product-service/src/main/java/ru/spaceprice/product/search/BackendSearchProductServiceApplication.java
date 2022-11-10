package ru.spaceprice.product.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class BackendSearchProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSearchProductServiceApplication.class, args);
    }

}
