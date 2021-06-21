package de.hft.softec.dbsys2.crimemap;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OffenseRepository extends JpaRepository<Offense, Long> {
    
}
