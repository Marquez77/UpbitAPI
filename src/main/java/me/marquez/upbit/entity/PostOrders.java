package me.marquez.upbit.entity;

import lombok.AllArgsConstructor;

import java.util.Date;

public class PostOrders {
    public record Request(
            //TO-DO 2023-08-11: 필수, 선택 요소들 @Nullable, @NonNull(또는 @NotNull) 붙이기
            String market,
            Side side,
            double volume,
            double price,
            OrderType ord_type,
            String identifier
    ) {
        @AllArgsConstructor
        public enum Side {
            BID("bid"),
            ASK("ask");
            private final String side;
        }
        @AllArgsConstructor
        public enum OrderType {
            LIMIT("limit"),
            PRICE("price"),
            MARKET("market");
            private final String ord_type;
        }
    }

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
            String side,
            String ord_type,
            double price,
            String state,
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
