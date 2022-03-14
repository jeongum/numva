package com.egongil.numva.core.entity.SafetyInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QRCode {
    @Id
    @GeneratedValue
    private Long id;
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_info_id")
    private SafetyInfo safetyInfo;
}
