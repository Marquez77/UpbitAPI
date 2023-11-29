package me.marquez.upbit.entity.quotation;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/**
 * 호가 정보 조회
 */
public class GetOrderBook {

    public static final String END_POINT = "v1/orderbook";

    /**
     * @param markets   마켓 코드 목록 (ex. KRW-BTC,BTC-ETH)
     */
    @Builder
    public record Request(
            @NonNull String[] markets
    ) {}

    /**
     * @param market            마켓 코드
     * @param timestamp         호가 생성 시각
     * @param total_ask_size    호가 매도 총 잔량
     * @param total_bid_size    호가 매수 총 잔량
     * @param orderbook_units   호가
     *                          orderbook_unit 리스트에는 15호가 정보가 들어가며 차례대로 1호가, 2호가 ... 15호가의 정보를 담고 있습니다.
     */
    @ToString
    public record Response(
            String market,
            long timestamp,
            double total_ask_size,
            double total_bid_size,
            OrderBook[] orderbook_units
    ) {
        /**
         * @param ask_price     매도호가
         * @param bid_price     매수호가
         * @param ask_size      매도 잔량
         * @param bid_size      매수 잔량
         */
        @ToString
        public record OrderBook(
                double ask_price,
                double bid_price,
                double ask_size,
                double bid_size
        ) {}
    }
}
