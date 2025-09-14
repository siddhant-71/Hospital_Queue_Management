package com.hospital.queue.Repository;

import com.hospital.queue.Entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    @Query(value = "SELECT * FROM hospital h " +
            "WHERE SIMILARITY(LOWER(h.name), LOWER(:input)) > 0.3 " +
            "ORDER BY SIMILARITY(LOWER(h.name), LOWER(:input)) DESC",
            nativeQuery = true)
    List<Hospital> fuzzySearch(@Param("input") String input);
//    @Query("SELECT h FROM Hospital h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))")
//    List<Hospital> searchByPartialName(@Param("name") String name);
}
