package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginWithOtpResponse implements Serializable {
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("first_time")
    @Expose
    private Integer firstTime;

    public Integer getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Integer firstTime) {
        this.firstTime = firstTime;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
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
