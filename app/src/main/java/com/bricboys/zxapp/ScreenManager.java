package com.bricboys.zxapp;

public class ScreenManager {
    public static String screenView;

    private ScreenManager(String screenView){
        this.screenView = screenView;
    }

    public static String verifyScreen(){
        return screenView;
    }
}
