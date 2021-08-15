package console.controller;

import console.domain.NaverMapApi;
import console.view.AddressView;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class NaverMapController {

    public void run() {
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String address = AddressView.inputAddress(inputStreamReader); // input 값 : 주소

            String apiUrl = NaverMapApi.getApiUrl(address);

            HttpURLConnection httpConnection = NaverMapApi.requestNaverMapApi(apiUrl);

            StringBuilder response = NaverMapApi.getNaverMapResponse(httpConnection);

            JSONArray jsonArray = NaverMapApi.jsonParse(response);

            AddressView.printJson(jsonArray);
        } catch (ProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

