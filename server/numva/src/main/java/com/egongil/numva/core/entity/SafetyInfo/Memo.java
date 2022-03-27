package com.egongil.numva.core.entity.SafetyInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {
    public Memo(String content) {
        this.content = content;
    }
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_info_id")
    private SafetyInfo safetyInfo;

    public void setSafetyInfo(SafetyInfo safetyInfo) {
        this.safetyInfo = safetyInfo;
    }
}
