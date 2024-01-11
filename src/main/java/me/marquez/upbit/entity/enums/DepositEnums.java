package me.marquez.upbit.entity.enums;

import lombok.AllArgsConstructor;

public class DepositEnums {

    /**
     * 입금 상태
     * - PROCESSING : 입금 진행중
     * - ACCEPTED : 완료
     * - CANCELLED : 취소됨
     * - REJECTED : 거절됨
     * - TRAVEL_RULE_SUSPECTED : 트래블룰 추가 인증 대기중
     * - REFUNDING : 반환절차 진행중
     * - REFUNDED : 반환됨
     */
    @AllArgsConstructor
    public enum State {
        PROCESSING,
        ACCEPTED,
        CANCELLED,
        REJECTED,
        TRAVEL_RULE_SUSPECTED,
        REFUNDING,
        REFUNDED
    }

    /**
     * 입출금 유형
     * - DEFAULT : 일반출금
     * - INTERNAL : 바로출금
     */
    @AllArgsConstructor
    public enum TransactionType {
        DEFAULT,
        INTERNAL
    }
}
