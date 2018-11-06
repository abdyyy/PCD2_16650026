package com.example.hopefoundation.rest;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostClient {

    private static final String TAG = PostClient.class.getSimpleName();

    public ResponseMessage getResponse(String Url, String finaljson) {
        ResponseMessage responseObject = new ResponseMessage();

        try {
            URL url = new URL(Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(finaljson);

            responseObject.setResponseMessage(urlConnection.getResponseMessage());
            responseObject.setStatusCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObject;
    }
}
