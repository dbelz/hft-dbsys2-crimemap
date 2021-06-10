package de.hft.softec.dbsys2.crimemap;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CrimeRepository extends CrudRepository<Crime, Long> {
 
    List<Crime> findAll();
    Crime findById(long id);
    List<Crime> findByZip(int zip);
    List<Crime> findByOffense(String offense);

}
