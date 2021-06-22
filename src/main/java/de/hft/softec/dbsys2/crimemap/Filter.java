package de.hft.softec.dbsys2.crimemap;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Filter {
    
    private long offense;
    private long district;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;

    public void setOffense(long id) {
        this.offense = id;
    }

    public long getOffense() {
        return this.offense;
    }

    public void setDistrict(long id) {
        this.district = id;
    }

    public long getDistrict() {
        return this.district;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

}
