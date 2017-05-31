package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 19.05.2017.
 */

public class Product {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("connection_type")
    @Expose
    private String connectionType;

    public Product(int id, String type, String name, String connectionType) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.connectionType = connectionType;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
