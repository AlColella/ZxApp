package com.bricboys.zxapp;

import android.graphics.Bitmap;

public class Magazine {
    private String mMagazineImage;
    private String mMagazineTitle;
    private String mMagazineIssue;
    private String mMagazineYear;

    public Magazine (String title, String issue, String year, String image) {
        mMagazineTitle = title;
        mMagazineIssue = issue;
        mMagazineYear = year;
        mMagazineImage = image;
    }

    public String getMagazineImage() {
        return mMagazineImage;
    }

    public void setMagazineImage(String mMagazineImage) {
        this.mMagazineImage = mMagazineImage;
    }

    public String getMagazineTitle() {
        return mMagazineTitle;
    }

    public void setMagazineTitle(String mMagazineTitle) {
        this.mMagazineTitle = mMagazineTitle;
    }

    public String getMagazineIssue() {
        return mMagazineIssue;
    }

    public void setMagazineIssue(String mMagazineIssue) {
        this.mMagazineIssue = mMagazineIssue;
    }

    public String getMagazineYear() {
        return mMagazineYear;
    }

    public void setMagazineYear(String mMagazineYear) {
        this.mMagazineYear = mMagazineYear;
    }
}
