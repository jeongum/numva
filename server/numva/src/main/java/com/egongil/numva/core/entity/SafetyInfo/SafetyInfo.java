package com.egongil.numva.core.entity.SafetyInfo;

import com.egongil.numva.core.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SafetyInfo {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "safetyInfo", fetch = FetchType.LAZY)
    private QRCode qrCode;

    @OneToOne(mappedBy = "safetyInfo", fetch = FetchType.LAZY)
    private Memo memo;
}
