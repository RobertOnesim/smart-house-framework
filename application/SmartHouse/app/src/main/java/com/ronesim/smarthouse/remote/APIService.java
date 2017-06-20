package com.ronesim.smarthouse.remote;

/**
 * Created by ronesim on 13.04.2017.
 */

import com.ronesim.smarthouse.model.Product;
import com.ronesim.smarthouse.model.Room;
import com.ronesim.smarthouse.model.Token;
import com.ronesim.smarthouse.model.User;
import com.ronesim.smarthouse.model.devices.Light;
import com.ronesim.smarthouse.model.devices.Lock;
import com.ronesim.smarthouse.model.devices.Thermostat;
import com.ronesim.smarthouse.model.devices.Webcam;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface APIService {

    // Login and Register system
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

    @POST("/api/auth/token/")
    @FormUrlEncoded
    Call<Token> getAccessToken(@Field("username") String username,
                               @Field("password") String password);

    // Room management
    @GET("/home")
    Call<List<Room>> roomList();

    @POST("/home/room/add/")
    @FormUrlEncoded
    Call<Room> addRoom(@Field("name") String name);

    @GET("/home/room/manage/{room_id}/")
    Call<Room> getRoom(@Path("room_id") int roomId);

    @DELETE("/home/room/manage/{room_id}/")
    Call<Room> deleteRoom(@Path("room_id") int roomId);

    // device management
    @GET("/home/device/manage")
    Call<List<Product>> productList();

    @DELETE("/home/device/manage/{device_type}/{device_id}/")
    Call<ResponseBody> deleteDevice(@Path("device_type") String deviceType,
                                    @Path("device_id") int deviceId);

    // light
    @GET("/home/device/light/{device_id}")
    Call<Light> getLight(@Path("device_id") int deviceId);

    @POST("/home/device/light/{device_id}/")
    @FormUrlEncoded
    Call<ResponseBody> updateLight(@Path("device_id") int deviceId,
                                   @Field("action") String action,
                                   @Field("state") String state,
                                   @Field("color") String color,
                                   @Field("intensity") Float intensity);

    // lock
    @GET("/home/device/lock/{device_id}")
    Call<Lock> getLock(@Path("device_id") int deviceId);

    @POST("/home/device/lock/{device_id}/")
    @FormUrlEncoded
    Call<ResponseBody> updateLock(@Path("device_id") int deviceId,
                                  @Field("action") String action,
                                  @Field("state") String state,
                                  @Field("pin_code") int pinCode);

    // thermostat
    @GET("/home/device/thermostat/{device_id}")
    Call<Thermostat> getThermostat(@Path("device_id") int deviceId);

    @POST("/home/device/thermostat/{device_id}/")
    @FormUrlEncoded
    Call<ResponseBody> updateThermostat(@Path("device_id") int deviceId,
                                        @Field("action") String action,
                                        @Field("state") String state,
                                        @Field("temperature") int temperature,
                                        @Field("humidity") int humidity);


    // webcam
    @GET("/home/device/webcam/{device_id}")
    Call<Webcam> getWebcam(@Path("device_id") int deviceId);

    @POST("/home/device/webcam/{device_id}/")
    @FormUrlEncoded
    Call<ResponseBody> updateWebcam(@Path("device_id") int deviceId,
                                    @Field("action") String action,
                                    @Field("state") String state);
}