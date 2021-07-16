package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessBean {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("user")
    @Expose
    private VerifyOtpResponse.Data data;

    public VerifyOtpResponse.Data getData() {
        return data;
    }

    public void setData(VerifyOtpResponse.Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
