package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoryResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pending_click_logs")
    @Expose
    private List<Log> pendingClickLogs = new ArrayList<>();
    @SerializedName("approved_click_logs")
    @Expose
    private List<Log> approvedClickLogs = new ArrayList<>();
    @SerializedName("rejected_click_logs")
    @Expose
    private List<Log> rejectedClickLogs = new ArrayList<>();

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Log> getPendingClickLogs() {
        return pendingClickLogs;
    }

    public void setPendingClickLogs(List<Log> pendingClickLogs) {
        this.pendingClickLogs = pendingClickLogs;
    }

    public List<Log> getApprovedClickLogs() {
        return approvedClickLogs;
    }

    public void setApprovedClickLogs(List<Log> approvedClickLogs) {
        this.approvedClickLogs = approvedClickLogs;
    }

    public List<Log> getRejectedClickLogs() {
        return rejectedClickLogs;
    }

    public void setRejectedClickLogs(List<Log> rejectedClickLogs) {
        this.rejectedClickLogs = rejectedClickLogs;
    }


    public class Log {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("click_log_id")
        @Expose
        private String clickLogId;
        @SerializedName("offer_id")
        @Expose
        private Integer offerId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("google_id")
        @Expose
        private Object googleId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("pay_out")
        @Expose
        private Object payOut;
        @SerializedName("payload_data")
        @Expose
        private Object payloadData;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("details")
        @Expose
        private Details details;
        @SerializedName("is_split")
        @Expose
        private Integer isSplit;
        @SerializedName("offer_payout_detail")
        @Expose
        private List<OfferPayoutDetail> offerPayoutDetail = new ArrayList<>();

        public Integer getIsSplit() {
            return isSplit;
        }

        public void setIsSplit(Integer isSplit) {
            this.isSplit = isSplit;
        }

        public List<OfferPayoutDetail> getOfferPayoutDetail() {
            return offerPayoutDetail;
        }

        public void setOfferPayoutDetail(List<OfferPayoutDetail> offerPayoutDetail) {
            this.offerPayoutDetail = offerPayoutDetail;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getClickLogId() {
            return clickLogId;
        }

        public void setClickLogId(String clickLogId) {
            this.clickLogId = clickLogId;
        }

        public Integer getOfferId() {
            return offerId;
        }

        public void setOfferId(Integer offerId) {
            this.offerId = offerId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getGoogleId() {
            return googleId;
        }

        public void setGoogleId(Object googleId) {
            this.googleId = googleId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Object getPayOut() {
            return payOut;
        }

        public void setPayOut(Object payOut) {
            this.payOut = payOut;
        }

        public Object getPayloadData() {
            return payloadData;
        }

        public void setPayloadData(Object payloadData) {
            this.payloadData = payloadData;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }

    }

    public class OfferPayoutDetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("offer_id")
        @Expose
        private Integer offerId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("click_log_id")
        @Expose
        private String clickLogId;
        @SerializedName("payout")
        @Expose
        private String payout;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("payout_date")
        @Expose
        private String payoutDate;
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

        public Integer getOfferId() {
            return offerId;
        }

        public void setOfferId(Integer offerId) {
            this.offerId = offerId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getClickLogId() {
            return clickLogId;
        }

        public void setClickLogId(String clickLogId) {
            this.clickLogId = clickLogId;
        }

        public String getPayout() {
            return payout;
        }

        public void setPayout(String payout) {
            this.payout = payout;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getPayoutDate() {
            return payoutDate;
        }

        public void setPayoutDate(String payoutDate) {
            this.payoutDate = payoutDate;
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

    public class Details {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("payout")
        @Expose
        private String payout;
        @SerializedName("expiry_date")
        @Expose
        private Object expiryDate;
        @SerializedName("percentage")
        @Expose
        private Object percentage;
        @SerializedName("message")
        @Expose
        private Object message;
        @SerializedName("tracking_link")
        @Expose
        private String trackingLink;
        @SerializedName("button_name")
        @Expose
        private String buttonName;
        @SerializedName("offer_for")
        @Expose
        private Integer offerFor;
        @SerializedName("advertiser_name")
        @Expose
        private String advertiserName;
        @SerializedName("categories")
        @Expose
        private String categories;
        @SerializedName("video_categories")
        @Expose
        private String videoCategories;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("display_image")
        @Expose
        private String displayImage;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("is_top_offer")
        @Expose
        private Integer isTopOffer;
        @SerializedName("is_unique")
        @Expose
        private Integer isUnique;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getPayout() {
            return payout;
        }

        public void setPayout(String payout) {
            this.payout = payout;
        }

        public Object getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Object expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Object getPercentage() {
            return percentage;
        }

        public void setPercentage(Object percentage) {
            this.percentage = percentage;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public String getTrackingLink() {
            return trackingLink;
        }

        public void setTrackingLink(String trackingLink) {
            this.trackingLink = trackingLink;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public Integer getOfferFor() {
            return offerFor;
        }

        public void setOfferFor(Integer offerFor) {
            this.offerFor = offerFor;
        }

        public String getAdvertiserName() {
            return advertiserName;
        }

        public void setAdvertiserName(String advertiserName) {
            this.advertiserName = advertiserName;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
        }

        public String getVideoCategories() {
            return videoCategories;
        }

        public void setVideoCategories(String videoCategories) {
            this.videoCategories = videoCategories;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Integer getIsTopOffer() {
            return isTopOffer;
        }

        public void setIsTopOffer(Integer isTopOffer) {
            this.isTopOffer = isTopOffer;
        }

        public Integer getIsUnique() {
            return isUnique;
        }

        public void setIsUnique(Integer isUnique) {
            this.isUnique = isUnique;
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

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }
}
