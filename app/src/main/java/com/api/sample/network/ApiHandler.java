package com.api.sample.network;

import com.api.sample.network.service.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static ApiHandler mInstance;

    // Это базовый урл, обычно его прописывают отдельно в доке Swagger, поэтому найти его не составит труда
    private static final String BASE_URL = "https://reqres.in/api/";

    private final Retrofit retrofit;

    public ApiHandler() {

        // Здесь мы описываем насколько много информации о выполнении запросы мы хотим видеть в логах
        // По сути тут ничего даже понимать не надо ctrl + c , ctrl + v
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);


        // Здесь мы конфигугируем штуку которая будет выполнять наши запросы
        // по сути мы выставляем различные настройки
        // если хотите переопределить какие-то уже готовые реализации на свои собственные ( как например обработка ошибок) то указываете это тут
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create()) // говорим ретрофиту что будем использовать GsonConverterFactory чтобы конвертировать json в наши java-классы
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // говорим ретрофиту чтобы он подтянул RxJavaAdapter
                .build();
    }

    // через этот метод будем получать экземпляр нашего ApiHandler
    public static ApiHandler getInstance() {
        if (mInstance == null) {
            mInstance = new ApiHandler();
        }
        return mInstance;
    }

    // метод возвращает сгенерированный на основе нашего ApiService класс
    // у которого мы будем вызвать запросы к Api
    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}
