package com.ronesim.smarthouse.remote;

/**
 * Created by ronesim on 13.04.2017.
 */

public class APIUtils {

    public static final String BASE_URL = "http://192.168.100.5:8000/";

    private APIUtils() {
    }

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
