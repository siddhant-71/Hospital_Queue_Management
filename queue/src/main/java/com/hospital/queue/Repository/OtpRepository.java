package com.hospital.queue.Repository;

import com.hospital.queue.Entities.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    OtpEntity findByInput(String input);
}
