package org.me.gcu.equakestartercode.models;

import java.util.Date;

public class Earthquake {
    private String title;
    private String description;
    private String link;
    private Date pubDate;
    private String category;
    private double geoLat;
    private double geoLong;

    public Earthquake(String title, String description, String link, Date pubDate, double geoLat, double geoLong){
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.category = "EQUK";
        this.geoLat = geoLat;
        this.geoLong = geoLong;
    }

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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
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
}
