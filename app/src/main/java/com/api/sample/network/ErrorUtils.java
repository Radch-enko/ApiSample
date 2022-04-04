package com.api.sample.network;

import com.api.sample.network.models.APIError;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorUtils {

    public static final Gson gson = new Gson();

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            APIError error = gson.fromJson(responseBody.string(), APIError.class);
            return error.message();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void handle(Throwable throwable, Expression onNetworkError, Expression onUnknownError) {
        if (throwable instanceof HttpException) {
            ResponseBody errorMessage = ((HttpException) throwable).response().errorBody();
            onNetworkError.invoke(getErrorMessage(errorMessage));
        } else {
            onUnknownError.invoke(throwable.getLocalizedMessage());
        }
    }
}
