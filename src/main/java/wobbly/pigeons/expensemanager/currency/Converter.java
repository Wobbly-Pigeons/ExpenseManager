package wobbly.pigeons.expensemanager.currency;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Converter {

    private static final OkHttpClient client = new OkHttpClient();

    public static void expenseCurrencyConverter (String base_code, String target_code, double amount) throws IOException {
        //below is the api website .com/MYAPIKEY/pair/CURRENCYSTART/CURRRENCYFINISH/AMOUNT
        // my key = 9d2cc16424081f3851cfd3ae
        String url = "https://v6.exchangerate-api.com/v6/9d2cc16424081f3851cfd3ae/pair/"
                + base_code + "/" + target_code + "/" + amount;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("key","9d2cc16424081f3851cfd3ae");
        urlBuilder.addQueryParameter("base_code",base_code);
        urlBuilder.addQueryParameter("target_code",target_code);
        //urlBuilder.addQueryParameter("amount",amount);
        url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println(responseBody);

    }


//    HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
//
//    //Adding some European currencies, namely:
//    //euro, danish crowns, icelandic crown, hungarian forint, swiss franc, moldovan leu,
//    //polish zloty, romanian leu, croatian kuna, bosnian convertible mark
//      currencyCodes.put(1, "EUR");
//      currencyCodes.put(2, "DKK");
////      currencyCodes.put(3, "ISK");
////      currencyCodes.put(4, "HUF");
////      currencyCodes.put(5, "CHF");
////      currencyCodes.put(6, "MDL");
////      currencyCodes.put(7, "PLN");
////      currencyCodes.put(8, "RON");
////      currencyCodes.put(9, "HRK");
////      currencyCodes.put(10, "BAM");
//
//    String fromCode, toCode;
//    double amount;
//
//    Scanner sc = new Scanner (System.in);
//
//    System.out.println("Enter the starting currency");
//    System.out.println("1:EUR (Euro)\t 2:DKK (Danish Kroner)");
//    fromCode = currencyCodes.get(sc.nextInt());
//
//      System.out.println("Enter the currency you want to convert to");
//      System.out.println("1:EUR (Euro)\t 2:DKK (Danish Kroner)");
//    toCode = currencyCodes.get(sc.nextInt());
//
//    System.out.println("Enter the amount");
//    amount = sc.nextFloat();
}
