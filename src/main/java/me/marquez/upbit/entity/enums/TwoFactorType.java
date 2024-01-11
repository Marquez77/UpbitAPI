package me.marquez.upbit.entity.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TwoFactorType {
    /**
     * 카카오 인증
     */
    @SerializedName("kakao")
    KAKAO,

    /**
     * 카카오 페이 인증
     */
    @SerializedName("kakao_pay")
    @Deprecated KAKAO_PAY,

    /**
     * 네이버 인증
     */
    @SerializedName("naver")
    NAVER;
}
