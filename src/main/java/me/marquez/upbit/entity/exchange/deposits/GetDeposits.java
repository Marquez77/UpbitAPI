package me.marquez.upbit.entity.exchange.deposits;

import me.marquez.upbit.entity.enums.DepositEnums;
import me.marquez.upbit.entity.enums.OrderBy;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 입금 리스트 조회
 */
public class GetDeposits {

    public static final String END_POINT = "v1/deposits";

    /**
     * @param currency  Currency 코드
     * @param state     입금 상태
     * @param uuids     입금 UUID의 목록
     * @param txids     입금 TXID의 목록
     * @param limit     개수 제한 (default: 100, max: 100)
     * @param page      페이지 수, default: 1
     * @param order_by  정렬
     */
    public record Request(
            @Nullable String currency,
            @Nullable DepositEnums.State state,
            @Nullable UUID[] uuids,
            @Nullable String[] txids,
            @Nullable int limit,
            @Nullable int page,
            @Nullable OrderBy order_by
    ) {}

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
