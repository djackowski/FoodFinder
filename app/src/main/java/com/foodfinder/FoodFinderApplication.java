package com.foodfinder;

import android.app.Application;

import com.foodfinder.rest.RetrofitRepository;

import retrofit2.Retrofit;

public class FoodFinderApplication extends Application  {
    private static RetrofitRepository retrofitRepository;

    public static Retrofit getRetrofit() {
        return retrofitRepository.getRetrofit();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitRepository = new RetrofitRepository();
        retrofitRepository.connect();


    }

}
