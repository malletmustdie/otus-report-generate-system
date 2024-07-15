package ru.elias.reportgenerator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.reportgenerator.service.client.ConfigControlServiceClient;
import ru.elias.reportgenerator.service.client.DataServiceClient;
import ru.elias.reportgenerator.service.client.ReportDataServiceClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${service.data-generation-service.base-uri}")
    private String dataServiceUri;

    @Value("${service.report-data-service.base-uri}")
    private String reportDataServiceUri;

    @Value("${service.config-control-service.base-uri}")
    private String configControlServiceUri;

    @Bean
    public DataServiceClient dataServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(dataServiceUri)
                .build();
        return buildDeclarativeRestClient(restClient, DataServiceClient.class);
    }

    @Bean
    public ReportDataServiceClient reportDataServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(reportDataServiceUri)
                .build();
        return buildDeclarativeRestClient(restClient, ReportDataServiceClient.class);
    }

    @Bean
    public ConfigControlServiceClient configControlServiceClient(RestClient.Builder restClientBuilder) {
        var restClient = restClientBuilder
                .baseUrl(configControlServiceUri)
                .build();
        return buildDeclarativeRestClient(restClient, ConfigControlServiceClient.class);
    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
