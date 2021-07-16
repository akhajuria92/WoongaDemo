package com.android.woonga.webapi;

import androidx.annotation.NonNull;

import com.android.woonga.utils.Utility;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class APICallBack<M> implements Callback<M> {


    @Override
    public void onResponse(@NonNull Call<M> call, @NonNull Response<M> response) {
        M model = response.body();


        if (model != null) {
            //  Utility.getInstance().showLog("Response: " + new Gson().toJson(response.body()));
            success(model);
        }
//         failure(Utility.getInstance().getSystemLabel(ApplicationDetails.getInstance().getContext().getResources().getString(R.string.general_err_msg)));

    }


    @Override
    public void onFailure(@NonNull Call<M> call, @NonNull Throwable throwable) {
        String errorType;
        String errorDesc;
        if (throwable instanceof SocketTimeoutException) {
            errorType = "Timeout";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IOException) {
            errorType = "IOException";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IllegalStateException) {
            errorType = "ConversionError";
            errorDesc = String.valueOf(throwable.getCause());
        } else {
            errorType = "Other Error";
            errorDesc = String.valueOf(throwable.getLocalizedMessage());
        }

//     onFailure(Utility.getInstance().getSystemLabel(ApplicationDetails.getInstance().getContext().getResources().getString(R.string.general_err_msg)));
    }

    /**
     * @param model if not null then sends model class to the activity
     */
    protected abstract void success(M model);

    /**
     * @param errorMsg if model class is null then send error message
     */
    protected abstract void failure(String errorMsg);

    /**
     * @param failureReason issues from throwable errors above
     */
    protected abstract void onFailure(String failureReason);
}
