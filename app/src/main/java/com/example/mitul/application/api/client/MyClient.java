package com.example.mitul.application.api.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PANDAV on 13-03-2018.
 */

public interface MyClient {

    //Actual Call
    @GET("Login/getpassword")
    Call<Object> getPassword(@Query("u_name") String username);

    //Test Call
    @GET("?results=10")
    Call<Object> getTestData();
}
