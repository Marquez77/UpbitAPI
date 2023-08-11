package me.marquez.upbit.entity;

import java.util.Date;
import java.util.List;

public class GetOrder {
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
                double price,
                double volume,
                double funds,
                String side,
                Date created_at
        ) {}
    }
}
