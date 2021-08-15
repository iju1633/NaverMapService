package console;

import console.controller.NaverMapController;
import console.view.AddressView;
import console.domain.*;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;


public class NaverMapApplication {

    public static void main(String[] args) {
        new NaverMapController().run();
    }

}
