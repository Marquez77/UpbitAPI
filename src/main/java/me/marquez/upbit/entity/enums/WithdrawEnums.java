package me.marquez.upbit.entity.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

public class WithdrawEnums {
    public enum State {
        /**
         * 대기중
         */
        WAITING,
        /**
         * 진행중
         */
        PROCESSING,
        /**
         * 완료
         */
        DONE,
        /**
         * 실패
         */
        FAILED,
        /**
         * 취소됨
         */
        CANCELLED,
        /**
         * 거절됨
         */
        REJECTED
    }
    @AllArgsConstructor
    public enum TransactionType {
        /**
         * 일반출금
         */
        @SerializedName("default")
        DEFAULT,

        /**
         * 바로출금
         */
        @SerializedName("internal")
        INTERNAL;
    }
}
