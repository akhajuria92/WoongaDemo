package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoriesResponse implements Serializable {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList<>();
    @SerializedName("success")
    @Expose
    private Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }


    public static class Category implements Serializable {

        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("unique_user")
        @Expose
        private Integer uniqueUser;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("color")
        @Expose
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public Integer getUniqueUser() {
            return uniqueUser;
        }

        public void setUniqueUser(Integer uniqueUser) {
            this.uniqueUser = uniqueUser;
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
