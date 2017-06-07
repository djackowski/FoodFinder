package com.foodfinder.rest;

import com.foodfinder.Food;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodService {
    //foods?Latitude=52.3989699&Longitude=17.2181015&Category=Italian
    @GET("foods")
    Observable<List<Food>> getFoods(@Query("Latitude") float latitude,
                                    @Query("Longitude") float longitude,
                                    @Query("Category") String category);

    @POST("foods")
    Observable<Response<Void>> saveFood(@Body Food food);
}
