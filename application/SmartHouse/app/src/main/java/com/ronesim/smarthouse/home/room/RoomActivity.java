package com.ronesim.smarthouse.home.room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.home.adapter.DeviceListAdapter;
import com.ronesim.smarthouse.home.adapter.util.ClickListener;
import com.ronesim.smarthouse.home.adapter.util.RecyclerTouchListener;
import com.ronesim.smarthouse.home.device.LightActivity;
import com.ronesim.smarthouse.home.device.ThermostatActivity;
import com.ronesim.smarthouse.model.Product;
import com.ronesim.smarthouse.model.Room;
import com.ronesim.smarthouse.model.devices.Device;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private APIService apiService;
    private List<Product> products = new ArrayList<>();

    private DeviceListAdapter adapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTokenAccess();

        // get room info
        Gson gson = new Gson();
        String strRoom = getIntent().getStringExtra("room");
        Room room = gson.fromJson(strRoom, Room.class);
        final List<Device> deviceList = room.getDevices();

        // set the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(RoomActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position < deviceList.size()) {
                    Intent intent = new Intent();
                    switch (deviceList.get(position).getType()) {
                        case "light":
                            intent = new Intent(view.getContext(), LightActivity.class);
                            break;
                        case "plug":
                            //intent = new Intent(view.getContext(), LightActivity.class);
                            break;
                        case "thermostat":
                            intent = new Intent(view.getContext(), ThermostatActivity.class);
                            break;
                        case "lock":
                            //intent = new Intent(view.getContext(), LightActivity.class);
                            break;
                        case "webcam":
                            //intent = new Intent(view.getContext(), LightActivity.class);
                            break;

                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("device_id", deviceList.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, final int position) {
                if (position < adapter.getItemCount() - 1) {
                    AlertDialog dialog = new AlertDialog.Builder(RoomActivity.this)
                            .setTitle("Remove device")
                            .setMessage("Would you like to delete this device?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Device device = adapter.getDevice(position);
                                    apiService.deleteDevice(device.getType(), device.getId()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 204) { // room deleted - status code returned 204
                                                adapter.remove(position);
                                            } else {
                                                Toast.makeText(RoomActivity.this, "Device id not found.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(RoomActivity.this, "Failed to remove the device.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
            }
        }));

        // Adapter
        apiService.productList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                // set adapter
                adapter = new DeviceListAdapter(deviceList, products, token);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
                Toast.makeText(getBaseContext(), "Failed to get products from server.", Toast.LENGTH_LONG).show();
            }
        });

        // Animate the Recycler View
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(600);
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setTokenAccess() {
        final String MY_PREFS_NAME = "prefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        token = prefs.getString("token", null);

        if (token != null && !token.isEmpty())
            apiService = APIUtils.getAPIService(token);
        else
            apiService = APIUtils.getAPIService();
    }
}
