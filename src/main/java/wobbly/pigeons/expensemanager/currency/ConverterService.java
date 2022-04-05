package wobbly.pigeons.expensemanager.currency;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConverterService {

    private final WebClient webClient;
    private String myAPIkey = "9d2cc16424081f3851cfd3ae";


    public ConverterService(WebClient.Builder builder){
        webClient = builder
                .baseUrl("https://v6.exchangerate-api.com/v6/" + myAPIkey)
                .build();
    }


    public Object getConversionAmount (String base_code, String target_code, double amount){
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
                .bodyToMono(Object)
                .block();
    }

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
