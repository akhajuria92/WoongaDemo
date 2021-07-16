package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralDetailsResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("amount_who_refer")
        @Expose
        private String amountWhoRefer;
        @SerializedName("amount_whom_refer")
        @Expose
        private String amountWhomRefer;
        @SerializedName("amount_new_user")
        @Expose
        private String amountNewUser;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAmountWhoRefer() {
            return amountWhoRefer;
        }

        public void setAmountWhoRefer(String amountWhoRefer) {
            this.amountWhoRefer = amountWhoRefer;
        }

        public String getAmountWhomRefer() {
            return amountWhomRefer;
        }

        public void setAmountWhomRefer(String amountWhomRefer) {
            this.amountWhomRefer = amountWhomRefer;
        }

        public String getAmountNewUser() {
            return amountNewUser;
        }

        public void setAmountNewUser(String amountNewUser) {
            this.amountNewUser = amountNewUser;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
