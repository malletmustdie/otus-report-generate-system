package ru.elias.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.elias.gateway.client.ConfigControlServiceClient;
import ru.elias.gateway.client.ReportDeliveryServiceClient;
import ru.elias.gateway.client.ReportGeneratorServiceClient;
import ru.elias.gateway.config.interceptor.ClientHeadersRequestInterceptor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${services.config-control-service.base-uri}")
    private String configControlServiceUri;

    @Value("${services.report-generator-service.base-uri}")
    private String reportGeneratorServiceUri;

    @Value("${services.report-delivery-service.base-uri}")
    private String reportDeliveryServiceUri;

    @Bean
    public ClientHeadersRequestInterceptor clientHeadersRequestInterceptor() {
        return new ClientHeadersRequestInterceptor();
    }

    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }

    @Bean
    public ConfigControlServiceClient configControlServiceClient(RestClient.Builder restClientBuilder) {
        var builder = restClientBuilder.baseUrl(configControlServiceUri);
        return createClient(builder, ConfigControlServiceClient.class);
    }

    @Bean
    public ReportGeneratorServiceClient reportGeneratorServiceClient(RestClient.Builder restClientBuilder) {
        var builder = restClientBuilder
                .baseUrl(reportGeneratorServiceUri)
                .requestInterceptor(clientHeadersRequestInterceptor());
        return createClient(builder, ReportGeneratorServiceClient.class);
    }

    @Bean
    public ReportDeliveryServiceClient reportDeliveryServiceClient(RestClient.Builder restClientBuilder) {
        var builder = restClientBuilder.baseUrl(reportDeliveryServiceUri);
        return createClient(builder, ReportDeliveryServiceClient.class);
    }

    private <T> T createClient(RestClient.Builder builder, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(builder.build());
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
