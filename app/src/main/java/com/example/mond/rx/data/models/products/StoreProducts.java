package com.example.mond.rx.data.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreProducts {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("pager")
    @Expose
    private Pager pager;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("suggestion")
    @Expose
    private Object suggestion;

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

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Object getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Object suggestion) {
        this.suggestion = suggestion;
    }
}
