package com.bricboys.zxapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bricboys.zxapp.General;
import com.bricboys.zxapp.ScreenManager;
import com.bricboys.zxapp.data.api.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralViewModel extends AndroidViewModel {
    private MutableLiveData<List<General>> generals = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public GeneralViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<General>> getGenerals() { return generals; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void search(String query) {
        isLoading.setValue(true);
        RetrofitClient.getZxinfoApi().searchGeneral(query, "compact", 100).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<General> list = new ArrayList<>();
                        JsonObject body = response.body();
                        Log.d("GeneralViewModel", "Response: " + body.toString());
                        
                        if (body.has("hits") && !body.get("hits").isJsonNull() && body.get("hits").isJsonObject()) {
                            JsonObject rootHits = body.getAsJsonObject("hits");
                            
                            if (rootHits.has("hits") && !rootHits.get("hits").isJsonNull() && rootHits.get("hits").isJsonArray()) {
                                JsonArray hitsArray = rootHits.getAsJsonArray("hits");
                                
                                for (int i = 0; i < hitsArray.size(); i++) {
                                    JsonElement hitElement = hitsArray.get(i);
                                    if (hitElement == null || !hitElement.isJsonObject()) continue;
                                    
                                    JsonObject hit = hitElement.getAsJsonObject();
                                    String id = hit.has("_id") && !hit.get("_id").isJsonNull() ? hit.get("_id").getAsString() : "";
                                    
                                    if (hit.has("_source") && !hit.get("_source").isJsonNull() && hit.get("_source").isJsonObject()) {
                                        JsonObject source = hit.getAsJsonObject("_source");
                                        
                                        String title = source.has("title") && !source.get("title").isJsonNull() ? source.get("title").getAsString() : "";
                                        String release = source.has("originalYearOfRelease") && !source.get("originalYearOfRelease").isJsonNull() ? source.get("originalYearOfRelease").getAsString() : "";
                                        
                                        // Extract Publisher
                                        StringBuilder pubBuilder = new StringBuilder();
                                        if (source.has("releases") && !source.get("releases").isJsonNull() && source.get("releases").isJsonArray()) {
                                            JsonArray releases = source.getAsJsonArray("releases");
                                            if (releases.size() > 0 && releases.get(0).isJsonObject()) {
                                                JsonObject firstRelease = releases.get(0).getAsJsonObject();
                                                if (firstRelease.has("publishers") && !firstRelease.get("publishers").isJsonNull() && firstRelease.get("publishers").isJsonArray()) {
                                                    JsonArray publishers = firstRelease.getAsJsonArray("publishers");
                                                    for (int j = 0; j < publishers.size(); j++) {
                                                        JsonElement pElem = publishers.get(j);
                                                        if (pElem != null && pElem.isJsonObject()) {
                                                            JsonObject pObj = pElem.getAsJsonObject();
                                                            if (pObj.has("name") && !pObj.get("name").isJsonNull()) {
                                                                if (pubBuilder.length() > 0) pubBuilder.append(", ");
                                                                pubBuilder.append(pObj.get("name").getAsString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        String available = source.has("availability") && !source.get("availability").isJsonNull() ? source.get("availability").getAsString() : "";
                                        String type = source.has("genreType") && !source.get("genreType").isJsonNull() ? source.get("genreType").getAsString() : "";
                                        String machine = source.has("machineType") && !source.get("machineType").isJsonNull() ? source.get("machineType").getAsString() : "";
                                        
                                        // Extract Image
                                        String imageId = "";
                                        String typeImage = "";
                                        if (source.has("screens") && !source.get("screens").isJsonNull() && source.get("screens").isJsonArray()) {
                                            JsonArray screens = source.getAsJsonArray("screens");
                                            for (int k = 0; k < screens.size(); k++) {
                                                JsonElement sElem = screens.get(k);
                                                if (sElem == null || !sElem.isJsonObject()) continue;
                                                JsonObject screen = sElem.getAsJsonObject();
                                                
                                                String format = screen.has("format") && !screen.get("format").isJsonNull() ? screen.get("format").getAsString() : "";
                                                String typeScreen = screen.has("type") && !screen.get("type").isJsonNull() ? screen.get("type").getAsString() : "";
                                                
                                                if (format.startsWith("Picture") && 
                                                    (typeScreen.equals("Hardware thumbnail") || 
                                                     (type.equals("Book") && typeScreen.equals("Loading screen")) ||
                                                     (ScreenManager.verifyScreen() != null && ScreenManager.verifyScreen().equals("loading") && typeScreen.equals("Loading screen")) ||
                                                     (ScreenManager.verifyScreen() != null && ScreenManager.verifyScreen().equals("running") && typeScreen.equals("Running screen")))) {
                                                    
                                                    if (screen.has("url") && !screen.get("url").isJsonNull()) {
                                                        imageId = screen.get("url").getAsString();
                                                        typeImage = typeScreen;
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        String scoreText = "";
                                        if (source.has("score") && !source.get("score").isJsonNull() && source.get("score").isJsonObject()) {
                                            JsonObject scoreObj = source.getAsJsonObject("score");
                                            scoreText = scoreObj.has("score") && !scoreObj.get("score").isJsonNull() ? scoreObj.get("score").getAsString() : "";
                                        }

                                        StringBuilder authBuilder = new StringBuilder();
                                        if (source.has("authors") && !source.get("authors").isJsonNull() && source.get("authors").isJsonArray()) {
                                            JsonArray authors = source.getAsJsonArray("authors");
                                            for (int m = 0; m < authors.size(); m++) {
                                                JsonElement aElem = authors.get(m);
                                                if (aElem != null && aElem.isJsonObject()) {
                                                    JsonObject aObj = aElem.getAsJsonObject();
                                                    if (aObj.has("name") && !aObj.get("name").isJsonNull()) {
                                                        if (authBuilder.length() > 0) authBuilder.append(", ");
                                                        authBuilder.append(aObj.get("name").getAsString());
                                                    }
                                                }
                                            }
                                        }

                                        list.add(new General(title, release, pubBuilder.toString(), available, type, machine, id, imageId, "", scoreText, authBuilder.toString(), typeImage));
                                    }
                                }
                            }
                        }
                        generals.setValue(list);
                    } catch (Exception e) {
                        Log.e("GeneralViewModel", "Data error", e);
                        error.setValue("Erro de dados: " + e.getMessage());
                    }
                } else {
                    error.setValue("Erro na busca: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Falha na rede: " + t.getMessage());
            }
        });
    }
}
