package me.marquez.upbit.entity;

import org.jetbrains.annotations.Nullable;

public class GetOrdersChance {
    /**
     * @param market    마켓 ID
     */
    public record Request(
            String market
    ) {}

    /**
     * @param bid_fee       매수 수수료 비율
     * @param ask_fee       매도 수수료 비율
     * @param market        마켓에 대한 정보
     * @param bid_account   매수 시 사용하는 화페의 계좌 상태
     * @param ask_account   매도 시 사용하는 화폐의 계좌 상태
     */
    public record Response(
            double bid_fee,
            double ask_fee,
            MarketInfo market,
            Account bid_account,
            Account ask_account
    ) {
        /**
         * @param id            마켓의 유일 키
         * @param name          마켓 이름
         * @param ask_types     매도 주문 지원 방식
         * @param bid_types     매수 주문 지원 방식
         * @param order_sides   지원 주문 종류    
         * @param bid           매수 시 제약 사항
         * @param ask           매도 시 제약 사항
         * @param max_total     최대 매도/매수 금액
         * @param state         마켓의 운영 상태
         */
        public record MarketInfo(
                String id,
                String name,
                String[] ask_types,
                String[] bid_types,
                String[] order_sides,
                Restrictions bid,
                Restrictions ask,
                double max_total,
                String state
        ) {
            /**
             * @param currency      화폐를 의미하는 영문 대문자 코드
             * @param price_unit    주문 금액 단위
             * @param min_total     최소 매도/매수 금액
             */
            public record Restrictions(
                    String currency,
                    @Nullable String price_unit,
                    double min_total
            ) {}
        }

        /**
         * @param currency                  화폐를 의미하는 영문 대문자 코드
         * @param balance                   주문 가능 금액/수량
         * @param locked                    주문 중 묶여있는 금액/수량
         * @param avg_buy_price             매수 평균가
         * @param avg_buy_price_modified    매수 평균가 수정 여부
         * @param unit_currency             평단가 기준 화폐
         */
        public record Account(
                String currency,
                double balance,
                double locked,
                double avg_buy_price,
                boolean avg_buy_price_modified,
                String unit_currency
        ) {}
    }
}
