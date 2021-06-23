package de.hft.softec.dbsys2.crimemap;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Offense {
    
    @Id
    private long id;

    private String name;

    protected Offense() { }

    public Offense(String name) {
        this.name = name;
    }

    public Offense(long id, String name) {
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
