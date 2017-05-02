package com.ronesim.smarthouse.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.model.Room;

import java.util.Collections;
import java.util.List;

/**
 * Created by ronesim on 21.04.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RoomHolder> {

    private List<Room> roomsList = Collections.emptyList();

    public RecyclerViewAdapter(List<Room> roomsList) {
        this.roomsList = roomsList;
    }

    @Override
    public RoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_row, parent, false);
        return new RoomHolder(v);

    }

    @Override
    public void onBindViewHolder(RoomHolder roomHolder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        roomHolder.setImageView(roomsList.get(position).getImageId());
        roomHolder.setTitle(roomsList.get(position).getName());
        roomHolder.setDate(roomsList.get(position).getDateUpdate());
        roomHolder.setDescription(roomsList.get(position).getInfo());
        roomHolder.setNumberOfDevices(roomsList.get(position).getNumberOfDevices());
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return roomsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Room room) {
        roomsList.add(position, room);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item
    public void remove(int position) {
        roomsList.remove(position);
        notifyItemRemoved(position);
    }

    public Room getRoom(int position) {
        return roomsList.get(position);
    }

}

