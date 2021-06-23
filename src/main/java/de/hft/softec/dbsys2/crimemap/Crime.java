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
    private Date dateOfCrime;

    @ManyToOne
    private District district;
    
    @Column(precision = 8, scale = 6)
    private BigDecimal lat;
    @Column(precision = 8, scale = 6)
    private BigDecimal lon;

    @ManyToOne
    private Offense offense;

    private String description;
    
    protected Crime() { }

    public Crime(Date dateOfCrime,
            District district, BigDecimal lat, BigDecimal lon,
            Offense offense) {
        this.dateOfCrime = dateOfCrime;
        this.district = district;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
    }

    public Crime(Date dateOfCrime,
            District district, BigDecimal lat, BigDecimal lon,
            Offense offense, String description) {
        this.dateOfCrime = dateOfCrime;
        this.district = district;
        this.lat = lat;
        this.lon = lon;
        this.offense = offense;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDateOfCrime(Date date) {
        this.dateOfCrime = date;
    }

    public Date getDateOfCrime() {
        return dateOfCrime;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public District getDistrict() {
        return district;
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
        return String.format("Crime[id=%d, date=%s, district=%s, latLon=%f,%f, offense=%s]",
            this.id,
            this.dateOfCrime,
            this.district.getName(),
            this.lat,
            this.lon,
            this.offense.getName());
    }

}
