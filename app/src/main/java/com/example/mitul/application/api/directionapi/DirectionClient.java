package com.example.mitul.application.api.directionapi;

import com.example.mitul.application.api.directionapi.response.GoogleDirectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PANDAV on 11-04-2018.
 */

public interface DirectionClient {
    @GET("directions/json")
    Call<GoogleDirectionResponse> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
