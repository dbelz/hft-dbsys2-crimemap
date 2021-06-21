package de.hft.softec.dbsys2.crimemap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class District {
    
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    protected District() { }

    public District(String name) {
        this.name = name;
    }

    public District(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
