package ru.elias.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.gateway.service.client.ConfigControlServiceClient;
import ru.elias.gateway.service.client.ReportDeliveryServiceClient;
import ru.elias.gateway.service.client.ReportGeneratorServiceClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${services.config-control-service.base-uri}")
    private String configControlServiceUri;

    @Value("${services.report-generator-service.base-uri}")
    private String reportGeneratorService;

    @Value("${services.report-delivery-service.base-uri}")
    private String reportDeliveryService;

    @Bean
    public ConfigControlServiceClient configControlServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(configControlServiceUri)
                .build();
        return buildDeclarativeRestClient(restClient, ConfigControlServiceClient.class);
    }

    @Bean
    public ReportGeneratorServiceClient reportGeneratorServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(reportGeneratorService)
                .build();
        return buildDeclarativeRestClient(restClient, ReportGeneratorServiceClient.class);
    }

    @Bean
    public ReportDeliveryServiceClient reportDeliveryServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(reportDeliveryService)
                .build();
        return buildDeclarativeRestClient(restClient, ReportDeliveryServiceClient.class);
    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
