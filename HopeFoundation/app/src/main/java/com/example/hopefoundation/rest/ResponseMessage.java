package com.example.hopefoundation.rest;

import com.example.hopefoundation.utils.JSONUtil;

import org.json.JSONObject;

public class ResponseMessage {

    private int statusCode;
    private String responseMessage;
    public static final String AUTHORIZATION_FAILED = "Authorization failed.";
    public static final String REDIRECTION_ERROR = "Redirection Error";
    public static final String CLIENT_ERROR = "Client Error";
    public static final String SERVER_ERROR = "Server error.";

    public ResponseMessage(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public ResponseMessage() {
        // TODO Auto-generated constructor stub
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public boolean isFailure () {
        return !isSuccess();
    }

    public boolean isSuccess() {
        return statusCode / 100 == 2;
    }

    public boolean isUnauthorized() {
        if (statusCode != 403) {
            return false;
        }
        return true;
    }

    public boolean isRedirectionError() {
        return statusCode / 100 == 3;
    }

    public boolean isClientError() {
        return statusCode / 100 == 4;
    }

    public boolean isServerError() {
        return statusCode / 100 == 5;
    }

    public JSONObject getResponseJson() {
        String responseMessage = getResponseMessage();
        return JSONUtil.getJSONObject(responseMessage);
    }

    public String getErrorType() {
        if (isUnauthorized()) {
            return AUTHORIZATION_FAILED;
        } else if (isRedirectionError()) {
            return REDIRECTION_ERROR;
        } else if (isClientError()) {
            return CLIENT_ERROR;
        } else if (isServerError()) {
            return SERVER_ERROR;
        }
        return null;
    }
}
