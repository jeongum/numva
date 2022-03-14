package com.egongil.numva.core.repository;

import com.egongil.numva.core.entity.SafetyInfo.SafetyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafetyInfoRepository extends JpaRepository<SafetyInfo, Long> {

}
