package console.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

import static utils.CommonsConstant.*;
import static utils.CommonsConstant.HTTP_STATE_CODE_200_OK;
import static utils.CommonsConstant.UTF_8;

public class NaverMapApi {

    private static final String NAVER_MAP_API_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";

    public static JSONArray jsonParse(StringBuilder response) {
        JSONTokener jsonTokener = new JSONTokener(response.toString());
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject.getJSONArray(KEY_ADDRESSES);
    }

    public static StringBuilder getNaverMapResponse(HttpURLConnection httpConnection) throws IOException {
        StringBuilder response;
        int responseCode = httpConnection.getResponseCode();
        BufferedReader bufferedReader;
        bufferedReader = getResponseData(httpConnection, responseCode);
        response = findErrorResponse(bufferedReader);
        bufferedReader.close();

        return response;
    }

    public static HttpURLConnection requestNaverMapApi(String apiUrl) throws IOException {
        HttpURLConnection httpConnection;
        URL url = new URL(apiUrl);
        httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod(HTTP_METHOD_GET);
        httpConnection.setRequestProperty(NAVER_MAP_API_KEY_ID, NAVER_MAP_CLIENT_ID);
        httpConnection.setRequestProperty(NAVER_MAP_API_KEY_SECRET, NAVER_MAP_CLIENT_SECRET);
        return httpConnection;
    }

    public static String getApiUrl(String address) throws UnsupportedEncodingException {
        String encodeAddress = URLEncoder.encode(address, UTF_8); // UTF_8로 인코딩
        return NAVER_MAP_API_URL + encodeAddress;
    }

    private static StringBuilder findErrorResponse(BufferedReader bufferedReader) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        return response;
    }

    private static BufferedReader getResponseData(HttpURLConnection httpConnection, int responseCode) throws IOException {
        if (responseCode == HTTP_STATE_CODE_200_OK) {
            return new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), UTF_8));
        }
        return new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
    }

}