package me.marquez.upbit.entity.exchange.withdraws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import me.marquez.upbit.entity.enums.WithdrawEnums;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * 원화 출금하기
 * 원화 출금을 요청한다. 등록된 출금 계좌로 출금된다.
 */
public class PostWithdrawsKRW {

    public static final String END_POINT = "v1/withdraws/krw";

    /**
     * @param amount            출금액
     * @param two_factor_type   2차 인증 수단
     */
    @Builder
    public record Request(
            @NonNull double amount,
            @NonNull TwoFactorType two_factor_type
    ) {
        @AllArgsConstructor
        public enum TwoFactorType {
            /**
             * 카카오 인증
             */
            KAKAO("kakao"),

            /**
             * 카카오 페이 인증
             */
            @Deprecated KAKAO_PAY("kakao_pay"),

            /**
             * 네이버 인증
             */
            NAVER("naver");

            private final String two_factor_type;
        }
    }

    /**
     * @param type              입출금 종류
     * @param uuid              출금의 고유 아이디
     * @param currency          화폐를 의미하는 영문 대문자 코드
     * @param txid              출금의 트랜잭션 아이디
     * @param state             출금 상태
     * @param created_at        출금 생성 시간
     * @param done_at           출금 완료 시간
     * @param amount            출금 금액/수량
     * @param fee               출금 수수료
     * @param transaction_type  출금 유형
     */
    public record Response(
            String type,
            UUID uuid,
            String currency,
            String txid,
            String state,
            Date created_at,
            Date done_at,
            double amount,
            double fee,
            WithdrawEnums.TransactionType transaction_type
    ) {}
}
