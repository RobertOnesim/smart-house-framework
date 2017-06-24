package com.ronesim.smarthouse.model.devices;

import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronesim on 06.06.2017.
 */

public class DeviceUpdateStateVisitor implements DeviceVisitor {
    private APIService apiService;
    private String action;
    private String state;

    public DeviceUpdateStateVisitor(String action, String state, String token) {
        this.action = action;
        this.state = state;

        if (token != null && !token.isEmpty())
            apiService = APIUtils.getAPIService(token);
        else
            apiService = APIUtils.getAPIService();
    }

    @Override
    public void visit(Light light) {
        apiService.updateLight(light.getId(), action, state, light.getColor(), (float) light.getIntensity())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            System.out.println("done");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("failed " + t.getMessage());
                    }
                });
    }

    @Override
    public void visit(Plug plug) {
        apiService.updatePlug(plug.getId(), action, state)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            System.out.println("done");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("failed " + t.getMessage());
                    }
                });

    }

    @Override
    public void visit(Thermostat thermostat) {
        apiService.updateThermostat(thermostat.getId(), "state", state, thermostat.getTemperature(), thermostat.getHumidity())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            System.out.println("done");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("failed " + t.getMessage());
                    }
                });
    }

    @Override
    public void visit(Lock lock) {
        apiService.updateLock(lock.getId(), "state", state, lock.getPinCode())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            System.out.println("done");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("failed " + t.getMessage());
                    }
                });
    }

    @Override
    public void visit(Webcam webcam) {
        apiService.updateWebcam(webcam.getId(), "state", state).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    System.out.println("done");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed " + t.getMessage());
            }
        });
    }
}
