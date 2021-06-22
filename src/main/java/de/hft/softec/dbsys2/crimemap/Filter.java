package de.hft.softec.dbsys2.crimemap;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Filter {
    
    private long offense = 0;
    private long district = 0;
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

    public String getOffenseString() {
        if (offense == 0) {
            return "";
        }
        return "" + offense;
    }

    public void setDistrict(long id) {
        this.district = id;
    }

    public long getDistrict() {
        return this.district;
    }

    public String getDistrictString() {
        if (district == 0) {
            return "";
        }
        return "" + district;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public String getDateStartString() {
        if (dateStart == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getDateEndString() {
        if (dateEnd == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
    }

}
