package com.egongil.numva.core.entity.SafetyInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SafetyNumber {
    public SafetyNumber(String safetyNumber) {
        this.safetyNumber = safetyNumber;
    }

    @Id
    @GeneratedValue
    Long id;
    String safetyNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_info_id")
    private SafetyInfo safetyInfo;

    public void setSafetyInfo(SafetyInfo safetyInfo) {
        this.safetyInfo = safetyInfo;
    }
}
