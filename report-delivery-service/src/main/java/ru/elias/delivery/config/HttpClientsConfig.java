package ru.elias.delivery.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.delivery.service.client.ReportDataServiceClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${services.report-data-service.base-uri}")
    private String reportDataService;

    @Bean
    public ReportDataServiceClient reportGeneratorServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(reportDataService)
                .build();
        return buildDeclarativeRestClient(restClient, ReportDataServiceClient.class);
    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
