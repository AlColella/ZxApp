package com.bricboys.zxapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bricboys.zxapp.Magazine;
import com.bricboys.zxapp.data.api.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MagazineViewModel extends AndroidViewModel {
    private MutableLiveData<List<Magazine>> magazines = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MagazineViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Magazine>> getMagazines() { return magazines; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void loadMagazines(String name) {
        isLoading.setValue(true);
        RetrofitClient.getZxinfoApi().getMagazine(name).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<Magazine> list = new ArrayList<>();
                        JsonObject source = response.body().getAsJsonObject("_source");
                        JsonArray issues = source.getAsJsonArray("issues");
                        for (int i = 0; i < issues.size(); i++) {
                            JsonObject obj = issues.get(i).getAsJsonObject();
                            String number = obj.has("number") ? obj.get("number").getAsString() : "";
                            String year = obj.has("date_year") ? obj.get("date_year").getAsString() : "";
                            String img = obj.has("cover_image") ? obj.get("cover_image").getAsString() : "";
                            list.add(new Magazine(name, number, year, img));
                        }
                        magazines.setValue(list);
                    } catch (Exception e) {
                        error.setValue("Erro ao processar dados");
                    }
                } else {
                    error.setValue("Revista não encontrada");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue(t.getMessage());
            }
        });
    }
}
