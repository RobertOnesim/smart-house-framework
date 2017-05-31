package com.ronesim.smarthouse.home.room;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.home.adapter.DeviceListAdapter;
import com.ronesim.smarthouse.model.Light;
import com.ronesim.smarthouse.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter
        List<Product> products = new ArrayList<>();
        Product product = new Product(1, "Light", "MagicBlue", "bluetooth");
        Product product1 = new Product(1, "Light", "Hue", "wifi");
        Product product2 = new Product(1, "Plug", "TP-Link", "wifi");
        products.add(product);
        products.add(product1);
        products.add(product2);
        List<Light> deviceList = new ArrayList<>();
        Light light = new Light("Magic Blue");
        deviceList.add(light);
        DeviceListAdapter adapter = new DeviceListAdapter(deviceList, products);
        recyclerView.setAdapter(adapter);

    }
}
