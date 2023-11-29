package me.marquez.upbit.entity.exchange.withdraws;

import lombok.Builder;
import lombok.ToString;
import me.marquez.upbit.entity.enums.OrderBy;
import me.marquez.upbit.entity.enums.WithdrawEnums;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * 출금 리스트 조회
 */
public class GetWithdraws {

    public static final String END_POINT = "v1/withdraws";

    /**
     * @param currency  Currency 코드
     * @param state     출금 상태
     * @param uuids     출금 UUID의 목록
     * @param txids     출금 TXID의 목록
     * @param limit     개수 제한 (default: 100, max: 100)
     * @param page      페이지 수, default: 1
     * @param order_by  정렬
     */
    @Builder
    public record Request(
            @Nullable String currency,
            @Nullable WithdrawEnums.State state,
            @Nullable UUID[] uuids,
            @Nullable String[] txids,
            @Nullable int limit,
            @Nullable int page,
            @Nullable OrderBy order_by
    ) {}

    /**
     * @param type              입출금 종류
     * @param uuid              출금의 고유 아이디
     * @param currency          화폐를 의미하는 영문 대문자 코드
     * @param net_type          출금 네트워크
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
            String net_type,
            String txid,
            WithdrawEnums.State state,
            Date created_at,
            Date done_at,
            double amount,
            double fee,
            WithdrawEnums.TransactionType transaction_type
    ) {}
}
