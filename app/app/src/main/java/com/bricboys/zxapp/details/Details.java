package com.bricboys.zxapp.details;

public class Details {
    private String mRunningScreen;
    private String mInstructions;
    private String mInlay;
    private String mLoadingScreen;
    private String mYouTube;

    public Details (String mRunningScreen,
                   String mInstructions,
                   String mInlay,
                    String mLoadingScreen,
                    String mYouTube) {
        this.mInlay = mInlay;
        this.mInstructions = mInstructions;
        this.mRunningScreen = mRunningScreen;
        this.mLoadingScreen = mLoadingScreen;
        this.mYouTube = mYouTube;
    }

    public String getmRunningScreen() {
        return mRunningScreen;
    }

    public void setmRunningScreen(String mRunningScreen) {
        this.mRunningScreen = mRunningScreen;
    }

    public String getmInstructions() {
        return mInstructions;
    }

    public void setmInstructions(String mInstructions) {
        this.mInstructions = mInstructions;
    }

    public String getmInlay() {
        return mInlay;
    }

    public String getmLoadingScreen() {
        return mLoadingScreen;
    }

    public String getmYouTube() {
        return mYouTube;
    }

}
