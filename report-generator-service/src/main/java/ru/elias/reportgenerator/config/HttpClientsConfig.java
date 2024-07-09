package ru.elias.reportgenerator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpClientsConfig {

    @Value("${gateway.service.billing-service.base-uri}")
    private String billingUri;

//    @Bean
//    public BillingClient billingClient(RestClient.Builder restClientBuilder) {
//        var restClient = restClientBuilder
//                .baseUrl(billingUri)
//                .build();
//        return buildDeclarativeRestClient(restClient, BillingClient.class);
//    }

    private <T> T buildDeclarativeRestClient(RestClient restClient, Class<T> targetClass) {
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return factory.createClient(targetClass);
    }

}
