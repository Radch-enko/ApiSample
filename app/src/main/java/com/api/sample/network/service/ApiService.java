package com.api.sample.network.service;

import com.api.sample.network.models.LoginBody;
import com.api.sample.network.models.LoginResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Этот интерфейс представляет собой описание всех запросов с бэка которые вы хотите выполнить
 * По сути сюда вы переписываете запросы которые нам дадут в Swagger
 */

/*
 * Response классы - классы в которых описано что приходит нам с бэка
 * Body классы - классы в которых описано что мы отправляем нам бэк ( к примеру чтобы залогиниться нам надо отправить email и password )
 * */
public interface ApiService {

    @POST("login")
        // тут описываем endpoint - эта та часть строки, которая остается если убрать из нее base url
    Single<LoginResponse> doLogin(@Body LoginBody registerBody);
}