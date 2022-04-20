package wobbly.pigeons.expensemanager.util;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class ConverterRestClient {

    private final WebClient webClient;
    private String myAPIkey = "9d2cc16424081f3851cfd3ae";


    public ConverterRestClient(){
        webClient = WebClient.builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/" + myAPIkey)
                .build();
    }

    public Double getConversionAmount (String baseCode, String targetCode, double amount){

        return (Double) webClient
                .get()
                // .uri("/pair/{base_code}/target_code/{amount}")
                .uri (uriBuilder -> uriBuilder
                        .path ("/pair")
                        .path("/" + baseCode)
                        .path("/" + targetCode)
                        .path("/" + amount)
                        .build()
                )
                .retrieve()
                .bodyToMono(Map.class)
                .map(map -> map.get("conversion_result"))
                .block();
    }
}