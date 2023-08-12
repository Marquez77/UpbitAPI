package me.marquez.upbit.entity.exchange.orders;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * 주문 취소 접수
 * 주문 UUID를 통해 해당 주문에 대한 취소 접수를 한다.
 */
public class DeleteOrder {
    /**
     * `uuid` 혹은 `identifier` 둘 중 하나의 값이 반드시 포함되어야 합니다.
     * @param uuid          취소할 주문의 UUID
     * @param identifier    조회용 사용자 지정 값
     */
    @Builder
    public record Request(
            @Nullable String uuid,
            @Nullable String identifier
    ) {}

    /**
     * @param uuid              주문의 고유 아이디
     * @param side              주문 종류
     * @param ord_type          주문 방식
     * @param price             주문 당시 화폐 가격
     * @param state             주문 상태
     * @param market            마켓의 유일 키
     * @param created_at        주문 생성 시간
     * @param volume            사용자가 입력한 주문 양
     * @param remaining_volume  체결 후 남은 주문 양
     * @param reserved_fee      수수료로 예약된 비용
     * @param remaining_fee     남은 수수료
     * @param paid_fee          사용된 수수료
     * @param locked            거래에 사용 중인 비용
     * @param executed_volume   체결된 양
     * @param trades_count      해당 주문에 걸린 체결 수
     */
    public record Response(
            String uuid,
            String side,
            String ord_type,
            double price,
            String state,
            String market,
            Date created_at,
            double volume,
            double remaining_volume,
            double reserved_fee,
            double remaining_fee,
            double paid_fee,
            double locked,
            double executed_volume,
            int trades_count
    ) {}
}
