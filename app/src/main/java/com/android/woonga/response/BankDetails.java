package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetails {
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("IFSC-Code")
    @Expose
    private String iFSCCode;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("bank_address")
    @Expose
    private String bankAddress;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getIFSCCode() {
        return iFSCCode;
    }

    public void setIFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
}
