package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardOffersResponse implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bgColor")
    @Expose
    private String bgColor;
    @SerializedName("topOffers")
    @Expose
    private List<Offers> topOffers = new ArrayList<>();
    @SerializedName("topRewardsOffers")
    @Expose
    private List<Offers> topRewardsOffers = new ArrayList<>();
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private VerifyOtpResponse.Data data;

    public VerifyOtpResponse.Data getData() {
        return data;
    }

    public void setData(VerifyOtpResponse.Data data) {
        this.data = data;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Offers> getTopOffers() {
        return topOffers;
    }

    public void setTopOffers(List<Offers> topOffers) {
        this.topOffers = topOffers;
    }

    public List<Offers> getTopRewardsOffers() {
        return topRewardsOffers;
    }

    public void setTopRewardsOffers(List<Offers> topRewardsOffers) {
        this.topRewardsOffers = topRewardsOffers;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class Offers implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("title_2")
        @Expose
        private String title2;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("short_description_3")
        @Expose
        private String shortDescription3;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("step_to_earn")
        @Expose
        private String stepToEarn;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("payout")
        @Expose
        private String payout;
        @SerializedName("payout_2")
        @Expose
        private String payout2;
        @SerializedName("payout_3")
        @Expose
        private String payout3;
        @SerializedName("rewards_after_how_many_days")
        @Expose
        private String rewardsAfterHowManyDays;
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
        private String  offerFor;
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
        @SerializedName("icon_2")
        @Expose
        private String icon2;
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
        @SerializedName("offer_coupon")
        @Expose
        private List<OfferCoupon> offerCoupon = new ArrayList<>();

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

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getShortDescription3() {
            return shortDescription3;
        }

        public void setShortDescription3(String shortDescription3) {
            this.shortDescription3 = shortDescription3;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStepToEarn() {
            return stepToEarn;
        }

        public void setStepToEarn(String  stepToEarn) {
            this.stepToEarn = stepToEarn;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPayout() {
            return payout;
        }

        public void setPayout(String payout) {
            this.payout = payout;
        }

        public String getPayout2() {
            return payout2;
        }

        public void setPayout2(String payout2) {
            this.payout2 = payout2;
        }

        public String getPayout3() {
            return payout3;
        }

        public void setPayout3(String payout3) {
            this.payout3 = payout3;
        }

        public String getRewardsAfterHowManyDays() {
            return rewardsAfterHowManyDays;
        }

        public void setRewardsAfterHowManyDays(String rewardsAfterHowManyDays) {
            this.rewardsAfterHowManyDays = rewardsAfterHowManyDays;
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

        public String  getOfferFor() {
            return offerFor;
        }

        public void setOfferFor(String  offerFor) {
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

        public String  getVideoCategories() {
            return videoCategories;
        }

        public void setVideoCategories(String  videoCategories) {
            this.videoCategories = videoCategories;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon2() {
            return icon2;
        }

        public void setIcon2(String icon2) {
            this.icon2 = icon2;
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

        public List<OfferCoupon> getOfferCoupon() {
            return offerCoupon;
        }

        public void setOfferCoupon(List<OfferCoupon> offerCoupon) {
            this.offerCoupon = offerCoupon;
        }
    }

    public class OfferCoupon implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("offer_id")
        @Expose
        private Integer offerId;
        @SerializedName("coupon_code")
        @Expose
        private String couponCode;
        @SerializedName("coupon_description")
        @Expose
        private String couponDescription;
        @SerializedName("expiry_date")
        @Expose
        private String expiryDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

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

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponDescription() {
            return couponDescription;
        }

        public void setCouponDescription(String couponDescription) {
            this.couponDescription = couponDescription;
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
