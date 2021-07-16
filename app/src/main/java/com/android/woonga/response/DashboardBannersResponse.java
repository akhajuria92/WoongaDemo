package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardBannersResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("topBanners")
    @Expose
    private List<TopBanner> topBanners = new ArrayList<>();
    @SerializedName("bottomBanners")
    @Expose
    private List<BottomBanner> bottomBanners = new ArrayList<>();
    @SerializedName("promotionalBanner")
    @Expose
    private PromotionalBanner promotionalBanner;
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TopBanner> getTopBanners() {
        return topBanners;
    }

    public void setTopBanners(List<TopBanner> topBanners) {
        this.topBanners = topBanners;
    }

    public List<BottomBanner> getBottomBanners() {
        return bottomBanners;
    }

    public void setBottomBanners(List<BottomBanner> bottomBanners) {
        this.bottomBanners = bottomBanners;
    }

    public PromotionalBanner getPromotionalBanner() {
        return promotionalBanner;
    }

    public void setPromotionalBanner(PromotionalBanner promotionalBanner) {
        this.promotionalBanner = promotionalBanner;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class BottomBanner implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("banner_name")
        @Expose
        private String bannerName;
        @SerializedName("button_name")
        @Expose
        private String buttonName;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("title_2")
        @Expose
        private String title2;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("icon_2")
        @Expose
        private String icon2;
        @SerializedName("tracking_link")
        @Expose
        private String trackingLink;
        @SerializedName("offer_for")
        @Expose
        private Integer offerFor;
        @SerializedName("advertiser_name")
        @Expose
        private String advertiserName;
        @SerializedName("categories")
        @Expose
        private String categories;
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
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("display_image")
        @Expose
        private String displayImage;
        @SerializedName("video")
        @Expose
        private Object video;
        @SerializedName("mediaType")
        @Expose
        private Integer mediaType;
        @SerializedName("image_position")
        @Expose
        private String  imagePosition;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("top_or_bottom")
        @Expose
        private Integer topOrBottom;
        @SerializedName("is_referal")
        @Expose
        private Integer isReferal;
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
        private List<DashboardOffersResponse.OfferCoupon> offerCoupon = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBannerName() {
            return bannerName;
        }

        public void setBannerName(String bannerName) {
            this.bannerName = bannerName;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
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

        public String getTrackingLink() {
            return trackingLink;
        }

        public void setTrackingLink(String trackingLink) {
            this.trackingLink = trackingLink;
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

        public void setStepToEarn(String stepToEarn) {
            this.stepToEarn = stepToEarn;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
        }

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
            this.video = video;
        }

        public Integer getMediaType() {
            return mediaType;
        }

        public void setMediaType(Integer mediaType) {
            this.mediaType = mediaType;
        }

        public String  getImagePosition() {
            return imagePosition;
        }

        public void setImagePosition(String imagePosition) {
            this.imagePosition = imagePosition;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Integer getTopOrBottom() {
            return topOrBottom;
        }

        public void setTopOrBottom(Integer topOrBottom) {
            this.topOrBottom = topOrBottom;
        }

        public Integer getIsReferal() {
            return isReferal;
        }

        public void setIsReferal(Integer isReferal) {
            this.isReferal = isReferal;
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

        public List<DashboardOffersResponse.OfferCoupon> getOfferCoupon() {
            return offerCoupon;
        }

        public void setOfferCoupon(List<DashboardOffersResponse.OfferCoupon> offerCoupon) {
            this.offerCoupon = offerCoupon;
        }

    }


    public class PromotionalBanner implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("tracking_link")
        @Expose
        private String trackingLink;
        @SerializedName("redirection_link")
        @Expose
        private String redirectionLink;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("icon")
        @Expose
        private String icon;
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

        public String getTrackingLink() {
            return trackingLink;
        }

        public void setTrackingLink(String trackingLink) {
            this.trackingLink = trackingLink;
        }

        public String getRedirectionLink() {
            return redirectionLink;
        }

        public void setRedirectionLink(String redirectionLink) {
            this.redirectionLink = redirectionLink;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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


    public class TopBanner implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("banner_name")
        @Expose
        private String bannerName;
        @SerializedName("button_name")
        @Expose
        private String buttonName;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("title_2")
        @Expose
        private String title2;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("icon_2")
        @Expose
        private String icon2;
        @SerializedName("tracking_link")
        @Expose
        private String trackingLink;
        @SerializedName("offer_for")
        @Expose
        private Integer offerFor;
        @SerializedName("advertiser_name")
        @Expose
        private String advertiserName;
        @SerializedName("categories")
        @Expose
        private String categories;
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
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("display_image")
        @Expose
        private String displayImage;
        @SerializedName("video")
        @Expose
        private Object video;
        @SerializedName("mediaType")
        @Expose
        private Integer mediaType;
        @SerializedName("image_position")
        @Expose
        private Integer imagePosition;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("top_or_bottom")
        @Expose
        private Integer topOrBottom;
        @SerializedName("is_referal")
        @Expose
        private Integer isReferal;
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
        private List<DashboardOffersResponse.OfferCoupon> offerCoupon = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBannerName() {
            return bannerName;
        }

        public void setBannerName(String bannerName) {
            this.bannerName = bannerName;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
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

        public String getTrackingLink() {
            return trackingLink;
        }

        public void setTrackingLink(String trackingLink) {
            this.trackingLink = trackingLink;
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

        public void setStepToEarn(String stepToEarn) {
            this.stepToEarn = stepToEarn;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
        }

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
            this.video = video;
        }

        public Integer getMediaType() {
            return mediaType;
        }

        public void setMediaType(Integer mediaType) {
            this.mediaType = mediaType;
        }

        public Integer getImagePosition() {
            return imagePosition;
        }

        public void setImagePosition(Integer imagePosition) {
            this.imagePosition = imagePosition;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Integer getTopOrBottom() {
            return topOrBottom;
        }

        public void setTopOrBottom(Integer topOrBottom) {
            this.topOrBottom = topOrBottom;
        }

        public Integer getIsReferal() {
            return isReferal;
        }

        public void setIsReferal(Integer isReferal) {
            this.isReferal = isReferal;
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

        public List<DashboardOffersResponse.OfferCoupon> getOfferCoupon() {
            return offerCoupon;
        }

        public void setOfferCoupon(List<DashboardOffersResponse.OfferCoupon> offerCoupon) {
            this.offerCoupon = offerCoupon;
        }
    }
}
