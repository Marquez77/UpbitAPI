package me.marquez.upbit.entity.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

public class OrderEnums {
    @AllArgsConstructor
    public enum State {
        /**
         * 체결 대기 (default)
         */
        @SerializedName("wait")
        WAIT,

        /**
         * 예약 주문 대기
         */
        @SerializedName("watch")
        WATCH,

        /**
         * 전체 체결 완료
         */
        @SerializedName("done")
        DONE,

        /**
         * 주문 취소
         */
        @SerializedName("cancel")
        CANCEL;
    }
    @AllArgsConstructor
    public enum Side {
        /**
         * 매수
         */
        @SerializedName("bid")
        BID,

        /**
         * 매도
         */
        @SerializedName("ask")
        ASK;
    }
    @AllArgsConstructor
    public enum OrderType {
        /**
         * 지정가 주문
         */
        @SerializedName("limit")
        LIMIT,

        /**
         * 시장가 주문(매수)
         */
        @SerializedName("price")
        PRICE,

        /**
         * 시장가 주문(매도)
         */
        @SerializedName("market")
        MARKET;
    }
}
