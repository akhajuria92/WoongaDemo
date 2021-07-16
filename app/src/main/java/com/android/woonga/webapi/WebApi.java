package com.android.woonga.webapi;


import com.android.woonga.response.AddVideoPointsResponse;
import com.android.woonga.response.AddVisitedResponse;
import com.android.woonga.response.CategoriesResponse;
import com.android.woonga.response.CategoryOffersResponse;
import com.android.woonga.response.CheckAppVersionResponse;
import com.android.woonga.response.ClickLogResponse;
import com.android.woonga.response.DashboardBannersResponse;
import com.android.woonga.response.DashboardOffersResponse;
import com.android.woonga.response.HistoryResponse;
import com.android.woonga.response.LoginWithOtpResponse;
import com.android.woonga.response.OfferDetailResponse;
import com.android.woonga.response.ReferralDetailsResponse;
import com.android.woonga.response.ResendOtpResponse;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.response.TicketDetailsResponse;
import com.android.woonga.response.TicketsResponse;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.response.WithdrawHistoryResponse;
import com.android.woonga.response.YoutubeTrendingResponse;
import com.google.gson.JsonElement;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebApi {

    @POST("loginWithOTP")
    Call<LoginWithOtpResponse> loginWithOtp(@Body HashMap hashMap);

    @POST("toVerifyOTP")
    Call<VerifyOtpResponse> verifyOtp(@Body HashMap hashMap);

    @POST("updateUserPhoneNumber")
    Call<VerifyOtpResponse> updateUserPhone(@Body HashMap hashMap);

    @POST("resendOTP")
    Call<ResendOtpResponse> resendOtp(@Body HashMap hashMap);

    @POST("OtpResendUpdateProfile")
    Call<ResendOtpResponse> resendUpdateProfileOtp(@Body HashMap hashMap);

    @POST("getDashboardBanners")
    Call<DashboardBannersResponse> getDashboardBanners(@Body HashMap hashMap);

    @POST("dashboardOffers")
    Call<DashboardOffersResponse> getDashboardOffers(@Body HashMap hashMap);

    @POST("getAllCategories")
    Call<CategoriesResponse> getCategories(@Body HashMap hashMap);

    @POST("offersByCategory")
    Call<CategoryOffersResponse> getOffers(@Body HashMap hashMap);

    @POST("addVisitedOffer")
    Call<AddVisitedResponse> addVisited(@Body HashMap hashMap);

    @POST("clicklogs/id")
    Call<ClickLogResponse> clickLog(@Body HashMap hashMap);

    @Multipart
    @POST("uploadImage")
    Call<JsonElement> uploadPhoto(@Part MultipartBody.Part body);

    @POST("updateprofile")
    Call<VerifyOtpResponse> updateProfile(@Body HashMap hashMap);

    @POST("update-account-details")
    Call<VerifyOtpResponse> updateAccountDetails(@Body HashMap hashMap);

    @POST("offerDetails")
    Call<OfferDetailResponse> offerDetails(@Body HashMap hashMap);

    @POST("addVideoPoints")
    Call<AddVideoPointsResponse> addVideoPoints(@Body HashMap hashMap);

    @POST("userLogHistory")
    Call<HistoryResponse> history(@Body HashMap hashMap);

    @POST("add-tickets")
    Call<SuccessBean> addTicket(@Body HashMap hashMap);

    @POST("my-tickets")
    Call<TicketsResponse> myTickets(@Body HashMap hashMap);

    @POST("ticket_comments")
    Call<TicketDetailsResponse> getTicketDetails(@Body HashMap hashMap);

    @POST("delete-tickets")
    Call<SuccessBean> deleteTicket(@Body HashMap hashMap);

    @POST("add-tickets-comments")
    Call<SuccessBean> addComment(@Body HashMap hashMap);

    @POST("to-verify-referral")
    Call<SuccessBean> verifyReferralCode(@Body HashMap hashMap);

    @POST("sent-withdrawl-request")
    Call<SuccessBean> sendWithdrawRequest(@Body HashMap hashMap);

    @POST("add-reviews")
    Call<SuccessBean> submitReview(@Body HashMap hashMap);

    @POST("user-withdrawl-listing")
    Call<WithdrawHistoryResponse> getWithdrawHistory(@Body HashMap hashMap);

    @GET("app-version")
    Call<CheckAppVersionResponse> checkAppVersion();

    @GET("referal-details")
    Call<ReferralDetailsResponse> getReferralDetails();

    @GET("videos")
    Call<YoutubeTrendingResponse> getTrendingVideos(@Query("part") String part, @Query("chart") String chart, @Query("regionCode") String regionCode,
                                                    @Query("maxResults") String maxResults, @Query("key") String key);

}