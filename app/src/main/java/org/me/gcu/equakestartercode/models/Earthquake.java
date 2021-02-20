package org.me.gcu.equakestartercode.models;

public class Earthquake {
    private String title;
    private String description;
    private String location;
    private Integer depth;
    private Double magnitude;
    private String link;
    private String pubDate;
    private String category;
    private double geoLat;
    private double geoLong;

    public Earthquake(){ }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }

    public String toString(){
        String str = "";
        str += "Title: " + getTitle() + " Description: " + getDescription() + " Link: " + getLink() +
                " PubDate: " + getPubDate() + "Category: " + getCategory() + " GeoLat: " + getGeoLat() + " GeoLong: " + getGeoLong();
        return str;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }
}
