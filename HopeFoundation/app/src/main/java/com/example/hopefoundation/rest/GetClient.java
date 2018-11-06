package com.example.hopefoundation.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetClient {

    private final String TAG = getClass().getSimpleName().toString();

    public ResponseMessage getResponse(String Url) {
        ResponseMessage responseObject = new ResponseMessage();
        try {
            URL url = new URL(Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            responseObject.setResponseMessage(urlConnection.getResponseMessage());
            responseObject.setStatusCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}

