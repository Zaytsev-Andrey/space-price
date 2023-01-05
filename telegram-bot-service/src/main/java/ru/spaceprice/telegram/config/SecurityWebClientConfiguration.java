package ru.spaceprice.telegram.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.spaceprice.telegram.property.FavoriteProductServiceConnectionProperty;
import ru.spaceprice.telegram.property.SearchProductServiceConnectionProperty;

@Configuration
@RequiredArgsConstructor
public class SecurityWebClientConfiguration {

    @Value("${default-client-registration}")
    private String defaultClientRegistrationId;

    private final SearchProductServiceConnectionProperty searchProductServiceConnectionProperty;

    private final FavoriteProductServiceConnectionProperty favoriteProductServiceConnectionProperty;

    @Bean
    public WebClient searchWebClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        return WebClient.builder()
                .baseUrl(searchProductServiceConnectionProperty.getUri())
                .filter(getOauth2Client(authorizedClientManager))
                .build();
    }

    @Bean WebClient favoriteWebClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        return WebClient.builder()
                .baseUrl(favoriteProductServiceConnectionProperty.getUri())
                .filter(getOauth2Client(authorizedClientManager))
                .build();
    }

    private ServerOAuth2AuthorizedClientExchangeFilterFunction getOauth2Client(
            ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2Client.setDefaultClientRegistrationId(defaultClientRegistrationId);

        return oauth2Client;
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService) {

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

}
