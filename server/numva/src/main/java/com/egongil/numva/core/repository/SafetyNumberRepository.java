package com.egongil.numva.core.repository;

import com.egongil.numva.core.entity.SafetyInfo.SafetyNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafetyNumberRepository extends JpaRepository<SafetyNumber, Long> {
}
