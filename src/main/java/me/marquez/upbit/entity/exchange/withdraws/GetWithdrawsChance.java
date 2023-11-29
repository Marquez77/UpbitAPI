package me.marquez.upbit.entity.exchange.withdraws;

import lombok.NonNull;
import lombok.ToString;

/**
 * 출금 가능 정보
 * 해당 통화의 가능한 출금 정보를 확인한다.
 */
public class GetWithdrawsChance {

    public static final String END_POINT = "v1/withdraws/chance";

    /**
     * @param currency  Currency symbol
     * @param net_type  출금 네트워크
     */
    public record Request(
            @NonNull String currency,
            @NonNull String net_type
    ) {}

    /**
     * @param member_level      사용자의 보안등급 정보
     * @param currency          화폐 정보
     * @param account           사용자의 계좌 정보
     * @param withdraw_limit    출금 제약 정보
     */
    public record Response(
            MemberLevel member_level,
            Currency currency,
            Account account,
            WithdrawLimit withdraw_limit
    ) {
        /**
         * @param security_level            보안등급
         * @param fee_level                 수수료 등급
         * @param email_verified            이메일 인증 여부
         * @param identity_auth_verified    실명 인증 여부    
         * @param bank_account_verified     계좌 인증 여부
         * @param kakao_pay_auth_verified   카카오페이 인증 여부
         * @param locked                    계정 보호 상태
         * @param wallet_locked             출금 보호 상태
         */
        public record MemberLevel(
                int security_level,
                int fee_level,
                boolean email_verified,
                boolean identity_auth_verified,
                boolean bank_account_verified,
                boolean kakao_pay_auth_verified,
                boolean locked,
                boolean wallet_locked
        ) {}

        /**
         * @param code              화폐를 의미하는 영문 대문자 코드
         * @param withdraw_fee      해당 화폐의 출금 수수료
         * @param is_coin           디지털 자산 여부
         * @param wallet_state      해당 화폐의 지갑 상태
         * @param wallet_support    해당 화폐가 지원하는 입출금 정보
         */
        public record Currency(
                String code,
                double withdraw_fee,
                boolean is_coin,
                String wallet_state,
                String[] wallet_support
        ) {}

        /**
         * @param currency                  화폐를 의미하는 영문 대문자 코드
         * @param balance                   주문가능 금액/수량
         * @param locked                    주문 중 묶여있는 금액/수량
         * @param avg_buy_price             매수평균가
         * @param avg_buy_price_modified    매수평균가 수정 여부
         * @param unit_currency             평단가 기준 화폐
         */
        public record Account(
                String currency,
                double balance,
                double locked,
                double avg_buy_price,
                boolean avg_buy_price_modified,
                String unit_currency
        ) {}

        /**
         * @param currency              화폐를 의미하는 영문 대문자 코드
         * @param minimum               최소 출금 금액/수량
         * @param onetime               1회 출금 한도
         * @param daily                 1일 출금 한도
         * @param remaining_daily       1일 잔여 출금 한도
         * @param remaining_daily_krw   통합 1일 잔여 출금 한도
         * @param fixed                 출금 금액/수량 소수점 자리 수
         * @param can_withdraw          출금 지원 여부
         */
        public record WithdrawLimit(
                String currency,
                double minimum,
                double onetime,
                double daily,
                double remaining_daily,
                double remaining_daily_krw,
                int fixed,
                boolean can_withdraw
        ) {}
    }
}
