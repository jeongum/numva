package com.egongil.numva.core.repository;

import com.egongil.numva.core.entity.SafetyInfo.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
}
