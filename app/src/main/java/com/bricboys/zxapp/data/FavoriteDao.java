package com.bricboys.zxapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT itemId FROM favorites")
    LiveData<List<String>> getAllFavoriteIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorites WHERE itemId = :itemId LIMIT 1")
    Favorite getFavoriteById(String itemId);
}
