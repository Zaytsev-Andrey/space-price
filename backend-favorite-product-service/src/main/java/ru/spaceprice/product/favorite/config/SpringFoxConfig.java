package ru.spaceprice.product.favorite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxConfig {

    @Value("${doc.info.title}")
    public String infoTitle;

    @Value("${doc.info.description}")
    public String infoDescription;

    @Value("${doc.info.version}")
    public String infoVersion;

    @Value("${doc.info.terms-of-service-url}")
    public String infoTermsOfServiceUrl;

    @Value("${doc.info.license}")
    public String infoLicense;

    @Value("${doc.info.licence-url}")
    public String infoLicenceUrl;

    @Value("${doc.contact.name}")
    public String contactName;

    @Value("${doc.contact.url}")
    public String contactUrl;

    @Value("${doc.contact.email}")
    public String contactEmail;

    @Value("${doc.api-key.name}")
    public String apiKeyName;

    @Value("${doc.api-key.keyname}")
    public String apiKeyKeyname;

    @Value("${doc.api-key.pass-as}")
    public String apiKeyPassAs;

    @Value("${doc.authorization-scope.scope}")
    public String authorizationScopeScope;

    @Value("${doc.authorization-scope.description}")
    public String authorizationScopeDescription;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(infoTitle,
                infoDescription,
                infoVersion,
                infoTermsOfServiceUrl,
                new Contact(contactName, contactUrl, contactEmail),
                infoLicense,
                infoLicenceUrl,
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey(apiKeyName, apiKeyKeyname, apiKeyPassAs);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope(authorizationScopeScope, authorizationScopeDescription);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(apiKeyKeyname, authorizationScopes));
    }
}
