package com.bricboys.zxapp.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ZxartApi {
    @GET
    Call<JsonObject> searchZxart(@Url String url);
}
