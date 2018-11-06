package com.example.hopefoundation.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hopefoundation.rest.GetClient;
import com.example.hopefoundation.rest.ResponseMessage;


public class FetchCentreStudentsService extends IntentService {
    public static final String CENTRES_STU = "centres_students";
    public static String DEBUG_TAG = FetchCentreStudentsService.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    public FetchCentreStudentsService() {
        super(DEBUG_TAG);
    }

    public static void startService(Activity context) {
        Intent intent = new Intent(context, FetchCentreStudentsService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("position", null);
        String[] temp;
        temp = username.split(" ");
        String url = "http://10.207.114.12:3000/api/students?centre="+temp[0];
        Log.d(DEBUG_TAG, "Url: " + url);
        GetClient getClient = new GetClient();
        ResponseMessage resp = getClient.getResponse(url);
        if (resp.isFailure()) {
            return;
        }
        String responseMessage = resp.getResponseMessage();
        Log.d(DEBUG_TAG, "Response: " + responseMessage);
        handleResponse(responseMessage);
    }

    public void handleResponse(String responseMessage) {
        // DynamicMenuEntryPref.getInstance().setMenuEntriesJSON(responseMessage).commit();
        sharedPreferences.edit().putString("all_centers_students", responseMessage.toString()).commit();
        Log.d(DEBUG_TAG, "success"+ responseMessage.toString());
        Intent success = new Intent(CENTRES_STU);
        sendBroadcast(success);

    }
}
