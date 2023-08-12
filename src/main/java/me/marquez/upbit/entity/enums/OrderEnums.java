package me.marquez.upbit.entity.enums;

import lombok.AllArgsConstructor;

public class OrderEnums {
    @AllArgsConstructor
    public enum State {
        /**
         * 체결 대기 (default)
         */
        WAIT("wait"),
        /**
         * 예약 주문 대기
         */
        WATCH("watch"),
        /**
         * 전체 체결 완료
         */
        DONE("done"),
        /**
         * 주문 취소
         */
        CANCEL("cancel");
        private final String state;
    }
    @AllArgsConstructor
    public enum Side {
        /**
         * 매수
         */
        BID("bid"),
        /**
         * 매도
         */
        ASK("ask");
        private final String side;
    }
    @AllArgsConstructor
    public enum OrderType {
        /**
         * 지정가 주문
         */
        LIMIT("limit"),
        /**
         * 시장가 주문(매수)
         */
        PRICE("price"),
        /**
         * 시장가 주문(매도)
         */
        MARKET("market");
        private final String ord_type;
    }
}
