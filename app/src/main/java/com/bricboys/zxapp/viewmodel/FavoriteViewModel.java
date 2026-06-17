package com.bricboys.zxapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bricboys.zxapp.data.AppDatabase;
import com.bricboys.zxapp.data.Favorite;
import com.bricboys.zxapp.data.FavoriteDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<List<String>> allFavoriteIds;
    private ExecutorService executorService;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        favoriteDao = db.favoriteDao();
        allFavorites = favoriteDao.getAllFavorites();
        allFavoriteIds = favoriteDao.getAllFavoriteIds();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    public LiveData<List<String>> getAllFavoriteIds() {
        return allFavoriteIds;
    }

    public void insert(Favorite favorite) {
        executorService.execute(() -> favoriteDao.insert(favorite));
    }

    public void delete(Favorite favorite) {
        executorService.execute(() -> favoriteDao.delete(favorite));
    }
    
    public void toggleFavorite(String itemId, String title, String type, String imageUrl) {
        executorService.execute(() -> {
            Favorite existing = favoriteDao.getFavoriteById(itemId);
            if (existing != null) {
                favoriteDao.delete(existing);
            } else {
                favoriteDao.insert(new Favorite(itemId, title, type, imageUrl, ""));
            }
        });
    }
}
