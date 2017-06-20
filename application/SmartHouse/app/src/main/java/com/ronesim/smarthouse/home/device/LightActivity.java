package com.ronesim.smarthouse.home.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.model.devices.Light;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightActivity extends AppCompatActivity {
    @BindView(R.id.deviceName)
    TextView deviceName;
    @BindView(R.id.deviceAddress)
    TextView deviceAddress;
    @BindView(R.id.redNumber)
    SeekBar redNumber;
    @BindView(R.id.redNumberView)
    TextView redNumberView;
    @BindView(R.id.greenNumber)
    SeekBar greenNumber;
    @BindView(R.id.greenNumberView)
    TextView greenNumberView;
    @BindView(R.id.blueNumber)
    SeekBar blueNumber;
    @BindView(R.id.blueNumberView)
    TextView blueNumberView;
    @BindView(R.id.setState)
    AppCompatButton setState;
    @BindView(R.id.intensityValue)
    EditText intensityValue;

    APIService apiService = APIUtils.getAPIService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        ButterKnife.bind(this);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get light info
        Bundle bundle = getIntent().getExtras();
        int deviceId = bundle.getInt("device_id");

        apiService.getLight(deviceId).enqueue(new Callback<Light>() {
            @Override
            public void onResponse(Call<Light> call, Response<Light> response) {
                final Light light = response.body();
                setData(light);
                // change state when user submit it
                setState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendState(light);
                    }
                });
            }

            @Override
            public void onFailure(Call<Light> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed to get data from server.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendState(Light light) {
        String red = redNumberView.getText().toString();
        String green = greenNumberView.getText().toString();
        String blue = blueNumberView.getText().toString();
        String[] colors = light.getColor().split(" ");

        float intensity = Float.valueOf(intensityValue.getText().toString());
        if (intensity < 0.0 && intensity > 1.0) {
            intensityValue.setError("Value between 0.0 and 1.0!");
            Toast.makeText(getBaseContext(), "Failed submit current state.", Toast.LENGTH_LONG).show();
            return;
        }

        String action;
        // change color
        if (!colors[0].equals(red) || !colors[1].equals(green) || !colors[2].equals(blue)) {
            action = "color";
        } else if (intensity != light.getIntensity()) {
            action = "intensity";
        } else {
            Toast.makeText(getBaseContext(), "No changes detected!", Toast.LENGTH_LONG).show();
            return;
        }

        if (light.isOn()) {
            apiService.updateLight(light.getId(), action, "on", red.concat(" ").concat(green).concat(" ").concat(blue), intensity).enqueue(new Callback<ResponseBody>() {
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

    private void setData(Light light) {
        deviceName.setText(light.getName());
        deviceAddress.setText(light.getMacAddress());

        final String[] color = light.getColor().split(" ");
        redNumberView.setText(color[0]);
        greenNumberView.setText(color[1]);
        blueNumberView.setText(color[2]);
        intensityValue.setText(String.valueOf(light.getIntensity()));

        redNumber.setProgress(Integer.valueOf(color[0]));
        redNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                seekBar.setProgress(progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                redNumberView.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        greenNumber.setProgress(Integer.valueOf(color[1]));
        greenNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                seekBar.setProgress(progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                greenNumberView.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        blueNumber.setProgress(Integer.valueOf(color[2]));
        blueNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                seekBar.setProgress(progressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                blueNumberView.setText(String.valueOf(seekBar.getProgress()));
            }
        });
    }

}
