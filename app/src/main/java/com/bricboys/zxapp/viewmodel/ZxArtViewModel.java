package com.bricboys.zxapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bricboys.zxapp.ZxArt;
import com.bricboys.zxapp.data.api.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZxArtViewModel extends AndroidViewModel {
    private MutableLiveData<List<ZxArt>> zxArts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public ZxArtViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ZxArt>> getZxArts() { return zxArts; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void search(String query) {
        isLoading.setValue(true);
        
        // Manual URL construction to bypass Retrofit path encoding issues
        String fullUrl = "https://zxart.ee/api/export:zxPicture/filter:zxPictureSearch=" + query.replace(" ", "%20");
        
        Log.d("ZxArtViewModel", "Requesting: " + fullUrl);

        RetrofitClient.getZxartApi().searchZxart(fullUrl).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<ZxArt> list = new ArrayList<>();
                        JsonObject respData = response.body().getAsJsonObject("responseData");
                        if (respData != null && respData.has("zxPicture")) {
                            JsonArray pictures = respData.getAsJsonArray("zxPicture");
                            for (int i = 0; i < pictures.size(); i++) {
                                JsonObject obj = pictures.get(i).getAsJsonObject();
                                String urlPic = obj.has("imageUrl") ? obj.get("imageUrl").getAsString() : "";
                                String title = obj.has("title") ? obj.get("title").getAsString() : "";
                                String year = obj.has("year") ? obj.get("year").getAsString() : "";
                                String rating = obj.has("rating") ? obj.get("rating").getAsString() : "";
                                list.add(new ZxArt(urlPic, title, rating, year, null));
                            }
                        }
                        zxArts.setValue(list);
                    } catch (Exception e) {
                        Log.e("ZxArtViewModel", "Parsing error", e);
                        error.setValue("Erro ao processar dados de arte");
                    }
                } else {
                    error.setValue("Erro na resposta do ZxArt: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("ZxArtViewModel", "Network failure", t);
                error.setValue("Falha na rede ZxArt: " + t.getMessage());
            }
        });
    }
}
