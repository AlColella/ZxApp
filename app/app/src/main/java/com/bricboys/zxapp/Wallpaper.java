package com.bricboys.zxapp;

public class Wallpaper {
    private String mRunningScreen;
    private String mLoadingScreen;


    public Wallpaper(String mRunningScreen, String mLoadingScreen){
        this.mLoadingScreen = mLoadingScreen;
        this.mRunningScreen = mRunningScreen;
    }

    public String getmRunningScreen() {
        return mRunningScreen;
    }

    public void setmRunningScreen(String mRunningScreen) {
        this.mRunningScreen = mRunningScreen;
    }

    public String getmLoadingScreen() {
        return mLoadingScreen;
    }

    public void setmLoadingScreen(String mLoadingScreen) {
        this.mLoadingScreen = mLoadingScreen;
    }
}
