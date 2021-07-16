package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryOffersResponse implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("offers")
    @Expose
    private List<DashboardOffersResponse.Offers> offers = new ArrayList<>();
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DashboardOffersResponse.Offers> getOffers() {
        return offers;
    }

    public void setOffers(List<DashboardOffersResponse.Offers> offers) {
        this.offers = offers;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
