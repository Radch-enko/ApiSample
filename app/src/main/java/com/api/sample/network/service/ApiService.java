package com.api.sample.network.service;

import com.api.sample.network.models.CountriesResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

/**
 * Этот интерфейс представляет собой описание всех запросов с бэка которые вы хотите выполнить
 * По сути сюда вы переписываете запросы которые нам дадут в Swagger
 */

/*
 * Response классы - классы в которых описано что приходит нам с бэка
 * Body классы - классы в которых описано что мы отправляем нам бэк ( к примеру чтобы залогиниться нам надо отправить email и password )
 * */
public interface ApiService {
    @GET("country?type=json")
    Observable<Map<String, String>> getCountries();
}