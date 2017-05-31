package com.ronesim.smarthouse.home.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronesim.smarthouse.R;

/**
 * Created by ronesim on 21.04.2017.
 */

class RoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private CardView cv;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView numberOfDevices;
    private ImageView imageView;

    RoomHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        date = (TextView) itemView.findViewById(R.id.dateUpdate);
        numberOfDevices = (TextView) itemView.findViewById(R.id.devices);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    void setImageView(int imageId) {
        this.imageView.setImageResource(imageId);
    }

    void setNumberOfDevices(int numberOfDevices) {
        String text = "Number of devices: " + numberOfDevices;
        this.numberOfDevices.setText(text);
    }

    void setDate(String date) {
        this.date.setText(date);
    }

    void setDescription(String description) {
        String text = "Last update: " + description;
        this.description.setText(text);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

}

