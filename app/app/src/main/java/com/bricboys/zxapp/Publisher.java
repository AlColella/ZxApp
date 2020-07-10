package com.bricboys.zxapp;

public class Publisher {

    private String mTitle;
    private String mRelease;
    private String mPublisher;
    private String mAvailability;
    private String mType;
    private String mMachine;
    private String mId;
    private String mImageId;
    private String mYoutubeLink;
    private String mScore;
    private String mAuthor;
    private String typeImage;

    public Publisher(String title, String release, String publisher,
                     String availability, String type, String machine, String id,
                     String imageId, String youtubeLink,
                     String score, String author, String typeImage) {
        mTitle = title;
        mRelease = release;
        mPublisher = publisher;
        mAvailability = availability;
        mType = type;
        mMachine = machine;
        mId = id;
        mImageId = imageId;
        mYoutubeLink = youtubeLink;
        mScore = score;
        mAuthor = author;
        this.typeImage = typeImage;
    }

    public String getTypeImage() {
        return this.typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmYoutubeLink() {
        return mYoutubeLink;
    }

    public String getmImageId() {
        return mImageId;
    }

    public void setmImageId(String mImageId) {
        this.mImageId = mImageId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmRelease() {
        return mRelease;
    }

    public void setmRelease(String mRelease) {
        this.mRelease = mRelease;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmAvailability() {
        return mAvailability;
    }

    public void setmAvailability(String mAvailability) {
        this.mAvailability = mAvailability;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmMachine() {
        return mMachine;
    }

    public void setmMachine(String mMachine) {
        this.mMachine = mMachine;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
