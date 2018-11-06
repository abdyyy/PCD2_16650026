package com.example.hopefoundation.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.hopefoundation.R;
import com.example.hopefoundation.activities.AdminActivity;
import com.example.hopefoundation.activities.SuperAdminActivity;
import com.example.hopefoundation.constants.ApiUrls;
import com.example.hopefoundation.rest.PostClient;
import com.example.hopefoundation.rest.ResponseMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 08-Nov-15.
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "LoginActivity";
    private static final String USERNAME = "username";
    private static final String TYPE = "type";
    private static final String PASSWORD = "password";
    public static final String MyPREFERENCES = "MyPrefs";

    private static JSONObject obj;
    private static String type = "";
    private final String mEmail;
    private final String mPassword;
    private final Activity context;
    private EditText etPassword;

    private SharedPreferences sharedPreferences;

    public UserLoginTask(String email, String password, Activity context, EditText etPassword) {
        mEmail = email;
        mPassword = password;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.etPassword = etPassword;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            String json;
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(USERNAME, mEmail);
            jsonObject.accumulate(PASSWORD, mPassword);
            json = jsonObject.toString();
            PostClient postClient = new PostClient();
            String url = ApiUrls.getLoginUrl();
            ResponseMessage responseMessage = postClient.getResponse(url, json);

            if (responseMessage.isFailure())
                return false;

            String result = responseMessage.getResponseMessage();
            obj = new JSONObject(result);
            type = obj.getString(TYPE);
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(USERNAME, mEmail);
            editor.commit();

            if (type.equals("A")) {
                Intent myIntent = new Intent(context, SuperAdminActivity.class);
                context.startActivity(myIntent);
                context.finish();
            } else if (type.equals("F")) {
                Intent myIntent = new Intent(context, AdminActivity.class);
                context.startActivity(myIntent);
                context.finish();
            }
        } else {
            etPassword.setError(context.getString(R.string.error_incorrect_password));
            etPassword.requestFocus();
        }
    }
}