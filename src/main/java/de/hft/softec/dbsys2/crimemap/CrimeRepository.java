package de.hft.softec.dbsys2.crimemap;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeRepository extends JpaRepository<Crime, Long> {
 
    public List<Crime> findAllByOffenseAndDateOfCrimeBetween(Offense offense, Date startDate, Date endDate);
    public List<Crime> findAllByDistrictAndDateOfCrimeBetween(District district, Date startDate, Date endDate);
    public List<Crime> findAllByOffenseAndDistrictAndDateOfCrimeBetween(Offense offense, District district, Date startDate, Date endDate);


}
