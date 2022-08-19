package ru.spaceprice.parser.diginetica;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.parser.Parser;
import ru.spaceprice.parser.diginetica.converter.ConverterDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaProductDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaResponseDto;
import ru.spaceprice.parser.diginetica.property.DigineticaConnectionProperty;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * Responsible for loading and converting product information from Diginetica store
 */
@Log4j2
public class DigineticaParser implements Parser {

    private final DigineticaConnectionProperty connectionProperties;

    private final ConverterDto converterDigineticaDtoToDto;

    private final WebClient client;

    public DigineticaParser(DigineticaConnectionProperty connectionProperties, ConverterDto converterDto) {
        this.connectionProperties = connectionProperties;
        this.converterDigineticaDtoToDto = converterDto;
        this.client = WebClient.create(connectionProperties.getSearchUri());
    }

    /**
     * Loads a list of products and converts to ProductDto format
     *
     * @param searchName - parameters for connecting to the online store
     * @return Flux<ProductDto> or empty Flux<ProductDto> if an error occurred
     */
    @Override
    public Flux<ProductDto> parseAllProducts(String searchName) {
        return invokeRequest(searchName)
                .flatMapIterable(DigineticaResponseDto::getProducts)
                .filter(oldiProductDto -> matchProduct(searchName, oldiProductDto))
                .map(converterDigineticaDtoToDto::convertToDto);
    }

    private Mono<DigineticaResponseDto> invokeRequest(String searchString) {
        // Set search query parameter
        connectionProperties.getQueryParams().put(connectionProperties.getSearchParamName(), searchString);

        return client
                .get()
                .uri(this::getURI)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DigineticaResponseDto.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofMillis(500))
                                .filter(throwable -> throwable instanceof TimeoutException)
                )
                .doOnError(this::exceptionHandle)
                .onErrorReturn(new DigineticaResponseDto());
    }

    /**
     * Create URI for search request
     * @param uriBuilder - initial UriBuilder
     * @return URI for search request
     */
    private URI getURI(UriBuilder uriBuilder) {
        uriBuilder.path(connectionProperties.getSearchPath());
        connectionProperties.getQueryParams().forEach(uriBuilder::queryParam);
        return uriBuilder.build();
    }

//    /**
//     * Loads product by productId and converts to ProductDto format
//     * @param productDto - product for reload
//     * @return Mono<ProductDto> or empty Mono<ProductDto> if an error occurred
//     */
//    @Override
//    public Mono<ProductDto> parseOneProduct(ProductDto productDto) {
//        return invokeRequest(productDto.getId(), Sort.price_asc, 0)
//                .map(DigineticaResponseDto::getProducts)
//                .filter(list -> list.size() > 0)
//                .map(list -> list.get(0))
//                .map(converterDigineticaDtoToDto::convertToDto);
//    }

    @Override
    public String getShopName() {
        return connectionProperties.getShopName();
    }

    private void exceptionHandle(Throwable throwable) {
        log.error(connectionProperties.getShopName() + " parser error");
    }

    private boolean matchProduct(String searchName, DigineticaProductDto digineticaProductDto) {
        String[] searchWords = searchName.split(" ");
        String productName = digineticaProductDto.getName().toLowerCase();
        for (int i = 0; i < searchWords.length; i++) {
            String searchWord = searchWords[i].toLowerCase();
            if (!productName.contains(searchWord.substring(0, searchWord.length() - 1))) {
                return false;
            }
        }
        return true;
    }
}
