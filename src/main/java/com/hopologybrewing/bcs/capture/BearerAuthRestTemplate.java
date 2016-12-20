package com.hopologybrewing.bcs.capture;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * {@code RestTemplate} supporting Basic Authentication.
 * <p/>
 * Simply extract the Basic Authentication feature from {@code TestRestTemplate}.
 * <p/>
 * Created by izeye on 15. 7. 1..
 */
public class BearerAuthRestTemplate extends RestTemplate {
    private void addAuthentication(String username, String password) {
        if (username == null) {
            return;
        }
        List<ClientHttpRequestInterceptor> interceptors = Collections
                .<ClientHttpRequestInterceptor>singletonList(
                        new BearerAuthorizationInterceptor());
        setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(),
                interceptors));
    }

    private static class BearerAuthorizationInterceptor implements
            ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Authorization", "Bearer " + new String("token"));
            return execution.execute(request, body);
        }

    }

}