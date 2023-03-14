package io.github.SilenceShine.shine.cloud.example;

import io.github.SilenceShine.shine.cloud.httpinterface.webclient.EnableWebclientHttpExchanges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author SilenceShine
 * @since 1.0
 */
@RestController
@SpringBootApplication
@EnableWebclientHttpExchanges
public class ExampleWebclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleWebclientApplication.class, args);
    }

    @Autowired
    private WebClientConfiguration.TestClient testClient;

    @GetMapping("/single")
    public Mono<ResponseEntity<Object>> single() {
        return testClient.single();
    }

}
