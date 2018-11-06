package com.example.hopefoundation.constants;

/**
 * Created by Ravi on 08-Nov-15.
 */
public class ApiUrls {
    public static final String BASE_URL = "http://10.207.114.12:3000";
    private static final String FETCH_STUDENTS_URL = "studentsByFaculty?username=";

    public static String getLoginUrl() {
        return BASE_URL + "/login";
    }

    public static String getFetchStudentsUrl() {
        return getBaseApiUrl() + FETCH_STUDENTS_URL;
    }

    private static String getBaseApiUrl() {
        return BASE_URL + "/api/";
    }


}
