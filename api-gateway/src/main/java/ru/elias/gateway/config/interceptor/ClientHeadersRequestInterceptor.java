package ru.elias.gateway.config.interceptor;

import io.micrometer.common.lang.NonNullApi;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@NonNullApi
public class ClientHeadersRequestInterceptor implements ClientHttpRequestInterceptor {

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Idempotency-Key", MDC.get("IdempotencyKey"));
        return execution.execute(request, body);
    }

}
