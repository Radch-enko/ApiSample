package com.api.sample.network;

import android.os.AsyncTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Я тут начал писать обертку чтобы не было дублирования кода
 * но запутался с лямбдами, а именно как передавать параметры в Runnable
 *
 * Будем дорабатывать
 * */

public class SafeCaller<T> {

    public void safeCall(Call<T> apiCall, Runnable onSuccess, Runnable onServerError, Runnable onUnexpectedError){
        AsyncTask.execute(() -> {
            apiCall.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful()){
                        onSuccess.run();
                    }else if (response.code() == 400){
                        onServerError.run();
                    }else{
                        onUnexpectedError.run();
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    onUnexpectedError.run();
                }
            });
        });
    }
}

