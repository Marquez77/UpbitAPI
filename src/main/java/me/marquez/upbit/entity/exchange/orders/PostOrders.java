package me.marquez.upbit.entity.exchange.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import me.marquez.upbit.entity.enums.OrderEnums;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * 주문하기
 * 주문 요청을 한다.
 */
public class PostOrders {

    public static final String END_POINT = "v1/orders";

    /**
     * @param market        마켓 ID (필수)
     * @param side          주문 종류 (필수)
     * @param volume        주문량 (지정가, 시장가 매도 시 필수)
     * @param price         주문 가격. (지정가, 시장가 매수 시 필수)
     *                      ex) KRW-BTC 마켓에서 1BTC당 1,000 KRW로 거래할 경우, 값은 1000 이 된다.
     *                      ex) KRW-BTC 마켓에서 1BTC당 매도 1호가가 500 KRW 인 경우, 시장가 매수 시 값을 1000으로 세팅하면 2BTC가 매수된다.
     *                      (수수료가 존재하거나 매도 1호가의 수량에 따라 상이할 수 있음)
     * @param ord_type      주문 타입 (필수)
     * @param identifier    조회용 사용자 지정값 (선택)
     */
    @Builder
    public record Request(
            @NonNull String market,
            @NonNull OrderEnums.Side side,
            @Nullable BigDecimal volume,
            @Nullable BigDecimal price,
            @NonNull OrderEnums.OrderType ord_type,
            @Nullable String identifier
    ) {}

    /**
     * @param uuid              주문의 고유 아이디
     * @param side              주문 종류
     * @param ord_type          주문 방식
     * @param price             주문 당시 화폐 가격
     * @param state             주문 상태
     * @param market            마켓의 유일키
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
            String uuid,
            OrderEnums.Side side,
            OrderEnums.OrderType ord_type,
            BigDecimal price,
            OrderEnums.State state,
            String market,
            OffsetDateTime crated_at,
            BigDecimal volume,
            BigDecimal remaining_volume,
            BigDecimal reserved_fee,
            BigDecimal remaining_fee,
            BigDecimal paid_fee,
            BigDecimal locked,
            BigDecimal executed_volume,
            int trades_count
    ) {}
}
