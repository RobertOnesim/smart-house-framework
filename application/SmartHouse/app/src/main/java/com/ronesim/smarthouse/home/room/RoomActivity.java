package com.ronesim.smarthouse.home.room;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.home.adapter.DeviceListAdapter;
import com.ronesim.smarthouse.model.Product;
import com.ronesim.smarthouse.model.Room;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private APIService apiService = APIUtils.getAPIService();
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        // get room info
        Gson gson = new Gson();
        String strRoom = getIntent().getStringExtra("room");
        final Room room = gson.fromJson(strRoom, Room.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter
        apiService.productList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                // set adapter
                DeviceListAdapter adapter = new DeviceListAdapter(room.getDevices(), products);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
                Toast.makeText(getBaseContext(), "Failed to get products from server.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
