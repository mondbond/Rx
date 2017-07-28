package com.example.mond.rx.data.models.stores;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stores {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("pager")
    @Expose
    private Pager storesPager;
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Pager getStoresPager() {
        return storesPager;
    }

    public void setStoresPager(Pager storesPager) {
        this.storesPager = storesPager;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
