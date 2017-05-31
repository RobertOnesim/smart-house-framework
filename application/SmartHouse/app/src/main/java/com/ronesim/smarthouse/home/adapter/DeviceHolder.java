package com.ronesim.smarthouse.home.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronesim.smarthouse.R;

/**
 * Created by ronesim on 07.05.2017.
 */

public class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    SwitchCompat turnOnOff;
    ImageView deviceLogo;
    ImageView addDevice;
    private CardView cv;
    private TextView name;

    DeviceHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.deviceName);
        addDevice = (ImageView) itemView.findViewById(R.id.addDevice);
        turnOnOff = (SwitchCompat) itemView.findViewById(R.id.turnOnOff);
        deviceLogo = (ImageView) itemView.findViewById(R.id.logoDevice);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

}