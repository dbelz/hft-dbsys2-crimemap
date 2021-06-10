package de.hft.softec.dbsys2.crimemap;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Crime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date dateTime;
    private int zip;
    private String city;
    private String address;

    @Column(precision = 8, scale = 6)
    private BigDecimal lat;
    @Column(precision = 8, scale = 6)
    private BigDecimal lon;

    // Einbrüche
    // Unfälle
    // Eigentumsdelikte
    // Sexualdelikte
    // Drogendelikte
    // Brände
    // Gewalttaten
    // Trunkenheit
    // Sachbeschädigungen
    private String offense;

    private String description;
    private String urlToPolicePressRelease;
    
    protected Crime() { }

    public Crime(Date dateTime,
            int zip, String city, String address, BigDecimal lat, BigDecimal lon,
            String offense) {
        this.dateTime = dateTime;
        this.zip = zip;
        this.city = city;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
    }

    public Crime(Date dateTime,
            int zip, String city, String address, BigDecimal lat, BigDecimal lon,
            String offense, String description) {
        this.dateTime = dateTime;
        this.zip = zip;
        this.city = city;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
        this.description = description;
    }

    public Crime(Date dateTime,
            int zip, String city, String address, BigDecimal lat, BigDecimal lon,
            String offense, String description, String urlToPolicePressRelease) {
        this.dateTime = dateTime;
        this.zip = zip;
        this.city = city;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
        this.description = description;
        this.urlToPolicePressRelease = urlToPolicePressRelease;
    }

    public long getId() {
        return id;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }

    public String getOffense() {
        return offense;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrlToPolicePressRelease(String urlToPolicePressRelease) {
        this.urlToPolicePressRelease = urlToPolicePressRelease;
    }

    public String getUrlToPolicePressRelease() {
        return urlToPolicePressRelease;
    }

    @Override
    public String toString() {
        return String.format("Crime[id=%d, zip=%s, city=%s, address=%s, latLon=%f,%f, offense=%s]",
            this.id,
            this.zip,
            this.city,
            this.address,
            this.lat,
            this.lon,
            this.offense);
    }

}
