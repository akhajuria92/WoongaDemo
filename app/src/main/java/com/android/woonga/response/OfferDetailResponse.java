package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferDetailResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("offers_details")
    @Expose
    private DashboardOffersResponse.Offers offersDetails;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DashboardOffersResponse.Offers getOffersDetails() {
        return offersDetails;
    }

    public void setOffersDetails(DashboardOffersResponse.Offers offersDetails) {
        this.offersDetails = offersDetails;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
