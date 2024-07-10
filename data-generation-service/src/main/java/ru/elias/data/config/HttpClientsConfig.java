package ru.elias.data.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.data.client.ExternalClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${data.service.external-service.base-uri}")
    private String externalUri;

    @Bean
    public ExternalClient billingClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(externalUri)
                .build();
        return buildDeclarativeRestClient(restClient, ExternalClient.class);
    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
