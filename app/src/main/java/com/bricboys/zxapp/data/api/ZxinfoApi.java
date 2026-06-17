package com.bricboys.zxapp.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZxinfoApi {
    @GET("search")
    Call<JsonObject> searchGeneral(@Query("query") String query, @Query("mode") String mode, @Query("size") int size);

    @GET("magazines/{name}")
    Call<JsonObject> getMagazine(@Path("name") String name);
}
