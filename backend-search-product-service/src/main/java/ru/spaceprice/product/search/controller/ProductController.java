package ru.spaceprice.product.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.search.service.ProductSearchService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductSearchService productSearchService;

    @PreAuthorize("hasAuthority('SCOPE_search-product')")
    @GetMapping(params = {"searchName"})
    public Flux<ProductDto> getProducts(@RequestParam("searchName") String searchName) {
        return productSearchService.findProductsByName(searchName);
    }
}
