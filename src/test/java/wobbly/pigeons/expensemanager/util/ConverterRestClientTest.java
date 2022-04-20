package wobbly.pigeons.expensemanager.util;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class ConverterRestClientTest {

    private static ConverterRestClient client;

    @BeforeAll
    public static void setUp() {
        client = new ConverterRestClient();
    }


    @Test
    public void givenCorrectValues_whenGetConvertAmount_thenSuccess() {
        //given
        String baseCode = "EUR";
        String targetCode = "DKK";
        double amount = 100;

        //when
        Long convertedAmount = client.getConversionAmount(baseCode, targetCode, amount);

        //then
        Assertions.assertNotNull(convertedAmount);
    }
}