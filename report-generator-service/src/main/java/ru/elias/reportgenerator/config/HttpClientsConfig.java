package ru.elias.reportgenerator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.reportgenerator.service.client.DataServiceClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${service.data-generation-service.base-uri}")
    private String dataServiceUri;

    @Bean
    public DataServiceClient dataServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(dataServiceUri)
                .build();
        return buildDeclarativeRestClient(restClient, DataServiceClient.class);
    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
