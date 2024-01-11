package me.marquez.upbit.entity.exchange.deposits;

import lombok.NonNull;
import me.marquez.upbit.entity.enums.DepositEnums;
import me.marquez.upbit.entity.enums.TwoFactorType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 원화 입금하기
 * 원화 입금을 요청한다.
 */
public class PostDepositsKRW {

    public static final String END_POINT = "v1/deposits/krw";

    /**
     * @param amount            입금액
     * @param two_factor_type   2차 인증 수단
     */
    public record Request(
            @NonNull BigDecimal amount,
            @NonNull TwoFactorType two_factor_type
    ) {}

    /**
     * @param type      입금 유형
     * @param uuid      입금의 고유 아이디
     * @param currency  화폐를 의미하는 영문 대문자 코드
     * @param txid      입금의 트랜잭션 아이디
     * @param state     입금 상태
     * @param created_at 입금 생성 시간
     * @param done_at   입금 완료 시간
     * @param amount    입금 금액/수량
     * @param fee       입금 수수료
     * @param transaction_type 입출금 유형
     */
    public record Response(
            String type,
            UUID uuid,
            String currency,
            String txid,
            DepositEnums.State state,
            OffsetDateTime created_at,
            OffsetDateTime done_at,
            BigDecimal amount,
            BigDecimal fee,
            DepositEnums.TransactionType transaction_type
    ) {}

}
