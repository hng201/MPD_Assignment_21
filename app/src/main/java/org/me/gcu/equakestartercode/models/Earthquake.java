package org.me.gcu.equakestartercode.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Shirley Ng S1626790
 */
@Entity(tableName = "earthquake")
public class Earthquake implements Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "earthquake_id")
    private Integer eid;
    @ColumnInfo(name = "location")
    private String location;
    private Integer depth;
    private Double magnitude;
    private String link;
    @ColumnInfo(name = "published_date")
    private String pubDate;
    private String category;
    @ColumnInfo(name = "geo_latitude")
    private double geoLat;
    @ColumnInfo(name = "geo_longitude")
    private double geoLong;

    public Earthquake(){ }

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
        str += "\nLocation: " + getLocation() + " Depth: " + getDepth() +
                "  Magnitude: " + getMagnitude() + " Link: " + getLink() + " PubDate: " + getPubDate().toString() + " Category: " + getCategory()
                + " GeoLat: " + getGeoLat() + " GeoLong: " + getGeoLong();
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

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }
}
