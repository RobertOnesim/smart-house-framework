package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 21.06.2017.
 */

public class HomeRule {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_set")
    @Expose
    private boolean isSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        this.isSet = set;
    }

    @Override
    public String toString() {
        return "HomeRule{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", isSet=" + isSet +
                '}';
    }


}
