import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.parser.Parser;
import ru.spaceprice.parser.diginetica.DigineticaParser;
import ru.spaceprice.parser.diginetica.converter.ConverterDigineticaDtoToDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaProductDto;
import ru.spaceprice.parser.diginetica.dto.DigineticaResponseDto;
import ru.spaceprice.parser.diginetica.property.DigineticaConnectionProperty;
import ru.spaceprice.parser.diginetica.property.OldiConnectionProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OldiParserTest {

    private static MockWebServer mockWebServer;

    private static ObjectMapper mapper;

    private Parser oldiParser;

    @BeforeAll
    public static void initWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void destroyWebServer() throws IOException {
        mockWebServer.close();
    }

    @BeforeEach
    public void init() {
        DigineticaConnectionProperty connectionProperties = new OldiConnectionProperty();
        connectionProperties.setShopName("Oldi");
        connectionProperties.setShopUri("https://www.oldi.ru");
        connectionProperties.setSearchUri(String.format("http://localhost:%s", mockWebServer.getPort()));
        connectionProperties.setSearchPath("");
        connectionProperties.setSearchParamName("st");
        connectionProperties.setSortParamName("sort");
        connectionProperties.setOffsetParamName("offset");
        connectionProperties.setQueryParams(new HashMap<>());

        ConverterDigineticaDtoToDto converterDto =
                new ConverterDigineticaDtoToDto(connectionProperties);
        mapper = new ObjectMapper();
        oldiParser = new DigineticaParser(connectionProperties, converterDto);
    }

    @Test
    public void findProductsTest() throws JsonProcessingException {
        DigineticaProductDto digineticaProductDto = new DigineticaProductDto(
                "1",
                "MacBook Pro",
                "200000",
                1.0,
                "/catalog/element/1",
                "https://img.oldi.ru/"
        );

        DigineticaResponseDto responseDto = new DigineticaResponseDto(1, List.of(digineticaProductDto));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mapper.writeValueAsString(responseDto))
                .addHeader("Content-Type", "application/json"));

        Flux<ProductDto> products = oldiParser.parseAllProducts("macbook");

        ProductDto productDto = new ProductDto(
                "1",
                "MacBook Pro",
                "200000",
                null,
                "https://www.oldi.ru/catalog/element/1",
                "https://img.oldi.ru/",
                "Oldi"
        );

        StepVerifier.create(products)
                .expectSubscription()
                .expectNextMatches(productDto::equals)
                .expectComplete()
                .verify();
    }

    @Test
    public void errorFindProductsTest() throws JsonProcessingException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setBody(mapper.writeValueAsString(new DigineticaResponseDto()))
                .addHeader("Content-Type", "application/json"));

        Flux<ProductDto> products = oldiParser.parseAllProducts("macbook");

        StepVerifier.create(products)
                .expectSubscription()
                .expectComplete()
                .verify();
    }

//    @Test
    public void findProductsOnBackpressureTest() throws JsonProcessingException {
        DigineticaProductDto digineticaProductDto1 = new DigineticaProductDto(
                "1",
                "MacBook Pro",
                "200000",
                1.0,
                "/catalog/element/1",
                "https://img.oldi.ru/"
        );

        DigineticaProductDto digineticaProductDto2 = new DigineticaProductDto(
                "2",
                "MacBook Air",
                "150000",
                1.0,
                "/catalog/element/2",
                "https://img.oldi.ru/"
        );


        DigineticaResponseDto responseDto =
                new DigineticaResponseDto(2, List.of(digineticaProductDto1, digineticaProductDto2));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mapper.writeValueAsString(responseDto))
                .addHeader("Content-Type", "application/json"));

        Flux<ProductDto> products = oldiParser.parseAllProducts("macbook");

        StepVerifier.create(products.onBackpressureBuffer(), 0)
                .expectSubscription()
                .thenRequest(1)
                .expectNextMatches(dto -> dto.getName().toLowerCase().contains("macbook"))
                .thenRequest(1)
                .expectNextMatches(dto -> dto.getName().toLowerCase().contains("macbook"))
                .expectComplete()
                .verify();
    }

}
