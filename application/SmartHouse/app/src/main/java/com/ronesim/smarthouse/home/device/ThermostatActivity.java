package com.ronesim.smarthouse.home.device;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.model.devices.Thermostat;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThermostatActivity extends AppCompatActivity {

    APIService apiService;
    @BindView(R.id.deviceName)
    TextView deviceName;
    @BindView(R.id.deviceAddress)
    TextView deviceAddress;
    @BindView(R.id.temperatureValue)
    SeekBar temperatureValue;
    @BindView(R.id.temperatureValueView)
    TextView temperatureValueView;
    @BindView(R.id.humidityValue)
    SeekBar humidityValue;
    @BindView(R.id.humidityValueView)
    TextView humidityValueView;
    @BindView(R.id.setState)
    AppCompatButton setState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
        ButterKnife.bind(this);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTokenAccess();

        // get light info
        Bundle bundle = getIntent().getExtras();
        int deviceId = bundle.getInt("device_id");

        apiService.getThermostat(deviceId).enqueue(new Callback<Thermostat>() {
            @Override
            public void onResponse(Call<Thermostat> call, Response<Thermostat> response) {
                final Thermostat thermostat = response.body();
                setData(thermostat);
                // change state when user submit it
                setState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendState(thermostat);
                    }
                });
            }

            @Override
            public void onFailure(Call<Thermostat> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to get data from server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendState(Thermostat thermostat) {
        int temperature = Integer.valueOf(temperatureValueView.getText().toString());
        int humidity = Integer.valueOf(humidityValueView.getText().toString());

        String action;
        // change temperature
        if (temperature != thermostat.getTemperature()) {
            action = "temperature";
        } else if (humidity != thermostat.getHumidity()) {
            action = "humidity";
        } else {
            Toast.makeText(getBaseContext(), "No changes detected!", Toast.LENGTH_LONG).show();
            return;
        }

        if (thermostat.isOn()) {
            apiService.updateThermostat(thermostat.getId(), action, "on", temperature, humidity).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful())
                        Toast.makeText(getBaseContext(), "Changes applied!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Cannot apply changes. Try again!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "Cannot apply changes on a closed device!", Toast.LENGTH_LONG).show();
        }
    }

    private void setData(Thermostat thermostat) {
        deviceName.setText(thermostat.getName());
        deviceAddress.setText(thermostat.getMacAddress());
        temperatureValueView.setText(String.valueOf(thermostat.getTemperature()));
        humidityValueView.setText(String.valueOf(thermostat.getHumidity()));

        temperatureValue.setProgress(thermostat.getTemperature());
        temperatureValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                seekBar.setProgress(progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                temperatureValueView.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        humidityValue.setProgress(thermostat.getHumidity());
        humidityValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                seekBar.setProgress(progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                humidityValueView.setText(String.valueOf(seekBar.getProgress()));
            }
        });
    }


    private void setTokenAccess() {
        final String MY_PREFS_NAME = "prefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null && !token.isEmpty())
            apiService = APIUtils.getAPIService(token);
        else
            apiService = APIUtils.getAPIService();
    }
}
