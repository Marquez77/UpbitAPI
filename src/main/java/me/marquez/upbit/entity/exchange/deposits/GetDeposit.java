package me.marquez.upbit.entity.exchange.deposits;

import me.marquez.upbit.entity.enums.DepositEnums;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 개별 입금 조회
 */
public class GetDeposit {

    public static final String END_POINT = "v1/deposit";

    /**
     * @param uuid      입금 UUID
     * @param txid      입금 TXID
     * @param currency  Currency 코드
     */
    public record Request(
            UUID uuid,
            String txid,
            String currency
    ) {}

    /**
     * @param type              입출금 종류
     * @param uuid              입금의 고유 아이디
     * @param currency          화폐를 의미하는 영문 대문자 코드
     * @param net_type          입금 네트워크
     * @param txid              입금의 트랜잭션 아이디
     * @param state             입금 상태
     * @param created_at        입금 생성 시간
     * @param done_at           입금 완료 시간
     * @param amount            입금 금액/수량
     * @param fee               입금 수수료
     * @param transaction_type  입금 유형
     */
    public record Response(
            String type,
            UUID uuid,
            String currency,
            String net_type,
            String txid,
            DepositEnums.State state,
            OffsetDateTime created_at,
            OffsetDateTime done_at,
            BigDecimal amount,
            BigDecimal fee,
            DepositEnums.TransactionType transaction_type
    ) {}

}
