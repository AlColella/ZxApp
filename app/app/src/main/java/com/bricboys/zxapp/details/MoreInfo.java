package com.bricboys.zxapp.details;

public class MoreInfo {
    private String Controls = "";
    private String numberOfPlayers = "";
    private String otherSystems = "";
    private String originalPrice = "";
    private String advertisement = "";

    public MoreInfo(String mControl,
                 String mNumberOfPlayers,
                 String mOtherSystems,
                 String mOriginalPrice,
                 String mAdvertisement)
    {
        this.Controls = mControl;
        this.numberOfPlayers = mNumberOfPlayers;
        this.otherSystems = mOtherSystems;
        this.originalPrice = mOriginalPrice;
        this.advertisement = mAdvertisement;
    }

    public String getControls() {
        return Controls;
    }

    public void setControls(String controls) {
        Controls = controls;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getOtherSystems() {
        return otherSystems;
    }

    public void setOtherSystems(String otherSystems) {
        this.otherSystems = otherSystems;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(String advertisement) {
        this.advertisement = advertisement;
    }

}
