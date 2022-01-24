package com.api.sample.network;

import com.api.sample.network.models.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ErrorUtils {

    static Retrofit retrofit;

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError("Произошла неизвестная ошибка! Попробуйте позже");
        }

        return error;
    }
}