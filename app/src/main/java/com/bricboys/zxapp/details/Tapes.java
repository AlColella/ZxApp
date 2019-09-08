package com.bricboys.zxapp.details;

public class Tapes {
    private String mSize;
    private String mUrl;
    private String mType;
    private String mFormat;
    private String mEncoding;
    private String mPublisher;
    private String mOrigin;

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }

    public Tapes(String mSize,
                 String mUrl,
                 String mType,
                 String mFormat,
                 String mEncoding,
                 String mPublisher,
                 String mOrigin)
    {
        this.mSize = mSize;
        this.mUrl = mUrl;
        this.mType = mType;
        this.mFormat = mFormat;
        this.mEncoding = mEncoding;
        this.mPublisher = mPublisher;
        this.mOrigin = mOrigin;
    }

    public String getmSize() {
        return mSize;
    }

    public void setmSize(String mSize) {
        this.mSize = mSize;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmFormat() {
        return mFormat;
    }

    public void setmFormat(String mFormat) {
        this.mFormat = mFormat;
    }

    public String getmEncoding() {
        return mEncoding;
    }

    public void setmEncoding(String mEncoding) {
        this.mEncoding = mEncoding;
    }

}
