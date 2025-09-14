package com.hospital.queue.Repository;

import com.hospital.queue.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Doctor findByEmail(String email);
    Doctor findByPhoneNumber(String phoneNumber);
    List<Doctor> findAllByHospitalId(Long hospital);
}
