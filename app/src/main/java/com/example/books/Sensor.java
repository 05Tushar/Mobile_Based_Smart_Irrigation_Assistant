package com.example.books;

public class Sensor
{
    private String value;
    private String longitude;
    private String latitude;
    private String crop;
    private String canal;

    public Sensor() {
    }

    public Sensor(String value, String longitude, String latitude, String crop, String canal) {
        this.value = value;
        this.longitude = longitude;
        this.latitude = latitude;
        this.crop = crop;
        this.canal= canal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCanal(){return canal;}

    public void setCanal(String canal) {this.canal =canal;}
}
