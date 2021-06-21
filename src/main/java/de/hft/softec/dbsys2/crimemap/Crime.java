package de.hft.softec.dbsys2.crimemap;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Crime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    @ManyToOne
    private District district;
    
    private String address;

    @Column(precision = 8, scale = 6)
    private BigDecimal lat;
    @Column(precision = 8, scale = 6)
    private BigDecimal lon;

    @ManyToOne
    private Offense offense;

    private String description;
    
    protected Crime() { }

    public Crime(Date dateTime,
            District district, String address, BigDecimal lat, BigDecimal lon,
            Offense offense) {
        this.dateTime = dateTime;
        this.district = district;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
    }

    public Crime(Date dateTime,
            District district, String address, BigDecimal lat, BigDecimal lon,
            Offense offense, String description) {
        this.dateTime = dateTime;
        this.district = district;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
        this.description = description;
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

    public void setDistrict(District district) {
        this.district = district;
    }

    public District getDistrict() {
        return district;
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

    public void setOffense(Offense offense) {
        this.offense = offense;
    }

    public Offense getOffense() {
        return offense;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Crime[id=%d, district=%s, address=%s, latLon=%f,%f, offense=%s]",
            this.id,
            this.district.getName(),
            this.lat,
            this.lon,
            this.offense.getName());
    }

}
