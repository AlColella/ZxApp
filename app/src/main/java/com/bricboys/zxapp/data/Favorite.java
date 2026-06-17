package com.bricboys.zxapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String itemId;
    public String title;
    public String type; // "ART", "MAGAZINE", "GENERAL"
    public String imageUrl;
    public String extraInfo; // JSON or simple string for other fields

    public Favorite(String itemId, String title, String type, String imageUrl, String extraInfo) {
        this.itemId = itemId;
        this.title = title;
        this.type = type;
        this.imageUrl = imageUrl;
        this.extraInfo = extraInfo;
    }
}
