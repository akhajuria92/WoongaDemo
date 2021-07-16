package com.android.woonga.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TicketDetailsResponse {
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
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("subject")
        @Expose
        private String subject;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("is_read")
        @Expose
        private Integer isRead;
        @SerializedName("is_comment")
        @Expose
        private Integer isComment;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("is_admin")
        @Expose
        private Integer isAdmin;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("comments")
        @Expose
        private List<Comment> comments = new ArrayList<>();

        public Integer getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(Integer isAdmin) {
            this.isAdmin = isAdmin;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public Integer getIsRead() {
            return isRead;
        }

        public void setIsRead(Integer isRead) {
            this.isRead = isRead;
        }

        public Integer getIsComment() {
            return isComment;
        }

        public void setIsComment(Integer isComment) {
            this.isComment = isComment;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
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

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

    }


    public static class Comment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("ticket_id")
        @Expose
        private Integer ticketId;
        @SerializedName("comment_by")
        @Expose
        private Integer commentBy;
        @SerializedName("images")
        @Expose
        private Object images;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("is_read")
        @Expose
        private Integer isRead;
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

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
        }

        public Integer getCommentBy() {
            return commentBy;
        }

        public void setCommentBy(Integer commentBy) {
            this.commentBy = commentBy;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Integer getIsRead() {
            return isRead;
        }

        public void setIsRead(Integer isRead) {
            this.isRead = isRead;
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
