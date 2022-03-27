package com.egongil.numva.core.entity.SafetyInfo;

import com.egongil.numva.core.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SafetyInfo {
    @Builder
    public SafetyInfo(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "safetyInfo", fetch = FetchType.LAZY)
    private QRCode qrCode;

    @OneToOne(mappedBy = "safetyInfo", fetch = FetchType.LAZY)
    private Memo memo;

    @OneToOne(mappedBy = "safetyInfo", fetch = FetchType.LAZY)
    private SafetyNumber safetyNumber;

    public void setUser(User user){
        this.user = user;
        user.addSafetyInfo(this);
    }

    public void setQRCode(QRCode qrCode){
        this.qrCode = qrCode;
        qrCode.setSafetyInfo(this);
    }

    public void setMemo(Memo memo){
        this.memo = memo;
        memo.setSafetyInfo(this);
    }

    public void setSafetyNumber(SafetyNumber safetyNumber){
        this.safetyNumber = safetyNumber;
        safetyNumber.setSafetyInfo(this);
    }

    public void changeName(String name) {
        this.name = name;
    }
}
