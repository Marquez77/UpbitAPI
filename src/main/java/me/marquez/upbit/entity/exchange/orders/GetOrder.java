package me.marquez.upbit.entity.exchange.orders;

import lombok.Builder;
import me.marquez.upbit.entity.enums.OrderEnums;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * 개별 주문 조회
 * 주문 UUID 를 통해 개별 주문 건을 조회한다.
 */
public class GetOrder {

    public static final String END_POINT = "v1/order";

    /**
     * `uuid` 혹은 `identifier` 둘 중 하나의 값이 반드시 포함되어야 합니다.
     * @param uuid          주문 UUID
     * @param identifier    조회용 사용자 지정 값
     */
    @Builder
    public record Request(
            @Nullable String uuid,
            @Nullable String identifier
    ) {}
    
    /**
     * @param uuid             주문의 고유 아이디
     * @param side             주문 종류
     * @param ord_type         주문 방식
     * @param price            주문 당시 화폐 가격
     * @param state            주문 상태
     * @param market           마켓의 유일키
     * @param created_at       주문 생성 시간
     * @param volume           사용자가 입력한 주문 양
     * @param remaining_volume 체결 후 남은 주문 양
     * @param reserved_fee     수수료로 예약된 비용
     * @param remaining_fee    남은 수수료
     * @param paid_fee         사용된 수수료
     * @param locked           거래에 사용 중인 비용
     * @param executed_volume  체결된 양
     * @param trades_count     해당 주문에 걸린 체결 수
     * @param trades           체결
     */
    public record Response(
            String uuid,
            OrderEnums.Side side,
            OrderEnums.OrderType ord_type,
            BigDecimal price,
            OrderEnums.State state,
            String market,
            OffsetDateTime created_at,
            BigDecimal volume,
            BigDecimal remaining_volume,
            BigDecimal reserved_fee,
            BigDecimal remaining_fee,
            BigDecimal paid_fee,
            BigDecimal locked,
            BigDecimal executed_volume,
            int trades_count,
            List<Trade> trades
    ) {
        /**
         * @param market     마켓의 유일 키
         * @param uuid       체결의 고유 아이디
         * @param price      체결 가격
         * @param volume     체결 양
         * @param funds      체결된 총 가격
         * @param side       체결 종류
         * @param created_at 체결 시각
         */
        public record Trade(
                String market,
                String uuid,
                BigDecimal price,
                BigDecimal volume,
                BigDecimal funds,
                OrderEnums.Side side,
                OffsetDateTime created_at
        ) {}
    }
}
