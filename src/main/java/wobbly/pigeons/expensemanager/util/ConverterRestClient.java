package wobbly.pigeons.expensemanager.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.function.Function;

public class ConverterRestClient {

    private final WebClient webClient;
    private String myAPIkey = "9d2cc16424081f3851cfd3ae";

//    public ConverterRestClient(WebClient webClient){
//        this.webClient = webClient;
//    }


    public ConverterRestClient(){
        webClient = WebClient.builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/" + myAPIkey)
                .build();
    }




    public Double getConversionAmount (String baseCode, String targetCode, double amount){
//        Function<Map, Object> function = new Function<>() {
//
//            @Override
//            public Object apply(Map map) {
//                return map.get("conversion_result");
//            }
//        };
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




   /*public Object getConversionAmount (String base_code, String target_code, double amount){
        return webClient
                .get()
                // .uri("/pair/{base_code}/target_code/{amount}")
                .uri (uriBuilder -> uriBuilder
                        .path ("/pair/")
                        .queryParam("base_code", base_code )
                        .queryParam ("target_code", target_code)
                        .queryParam("amount", amount )
                        .build()
                )
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }*/




    //below from march9 code "CurrencyApp"
   /* public Rates getLatestRats(){
        return webClient
                .get()
                .uri("/")
                .retrieve()
                .bodyToMono(Rates.class)
                .block();
    }
*/
}
