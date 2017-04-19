package com.ronesim.smarthouse.remote;

/**
 * Created by ronesim on 13.04.2017.
 */

import com.ronesim.smarthouse.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface APIService {

    @POST("/api/register/")
    @FormUrlEncoded
    Call<User> registerUser(@Field("username") String username,
                            @Field("email") String email,
                            @Field("email2") String email2,
                            @Field("password") String password);

    @POST("/api/login/")
    @FormUrlEncoded
    Call<User> loginUser(@Field("username") String username,
                         @Field("password") String password);

}