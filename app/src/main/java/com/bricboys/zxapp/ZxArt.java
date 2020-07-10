package com.bricboys.zxapp;

public class ZxArt {
    private String mPictureImage;
    private String mTitle;
    private String mRating;
    private String mYear;
    private String mAuthorId;

    public ZxArt(String mPictureImage, String mTitle, String mRating, String mYear, String mAuthorId) {
        this.mPictureImage = mPictureImage;
        this.mTitle = mTitle;
        this.mRating = mRating;
        this.mYear = mYear;
        this.mAuthorId = mAuthorId;
    }

    public String getmPictureImage() {
        return mPictureImage;
    }

    public void setmPictureImage(String mPictureImage) {
        this.mPictureImage = mPictureImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public void setmAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }
}
