package me.marquez.upbit.entity.exchange.orders;

import lombok.*;
import me.marquez.upbit.entity.enums.OrderBy;
import me.marquez.upbit.entity.enums.OrderEnums;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * 주문 리스트 조회
 * 주문 리스트를 조회한다.
 */
public class GetOrders {

    public static final String END_POINT = "v1/orders";

    /**
     * @param market        마켓 아이디
     * @param uuids         주문 UUID의 목록
     * @param identifiers   주문 identifier의 목록
     * @param state         주문 상태
     *                      - 주문 리스트 조회 API에서 시장가 주문이 조회되지 않는경우:
     *                          시장가 매수 주문은 체결 후 주문 상태가 cancel, done 두 경우 모두 발생할 수 있습니다.
     *                      - 시장가로 체결이 일어난 후 주문 잔량이 발생하는 경우, 남은 잔량이 반환되며 cancel 처리됩니다.
     *                        대부분의 경우 소수점 아래 8자리까지 나누어 떨어지지 않는 미미한 금액이 주문 잔량으로 발생하게 됩니다.
     *                      - 만일 주문 잔량 없이 딱 맞아 떨어지게 체결이 발생한 경우에는 주문 상태가 done이 됩니다.
     * @param states        주문 상태의 목록
     *                      * 미체결 주문(wait, watch)과 완료 주문(done, cancel)은 혼합하여 조회하실 수 없습니다.
     * @param page          페이지 수, default: 1
     * @param limit         요청 개수, default: 100
     * @param order_by      정렬 방식
     */
    @Builder
    public record Request(
            @Nullable String market,
            @Nullable UUID[] uuids,
            @Nullable UUID[] identifiers,
            @Nullable OrderEnums.State state,
            @Nullable OrderEnums.State[] states,
            @Nullable int page,
            @Nullable int limit,
            @Nullable OrderBy order_by
    ) {}

    /**
     * @param uuid              주문의 고유 아이디
     * @param side              주문 종류
     * @param ord_type          주문 방식
     * @param price             주문 당시 화폐 가격
     * @param state             주문 상태
     * @param market            마켓의 유일 키
     * @param crated_at         주문 생성 시간
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
            UUID uuid,
            OrderEnums.Side side,
            OrderEnums.OrderType ord_type,
            double price,
            OrderEnums.State state,
            String market,
            Date crated_at,
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
