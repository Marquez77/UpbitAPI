package me.marquez.upbit.entity;

public class GetAccounts {

    /**
     * @param currency               화폐를 의미 하는 영문 대문자 코드
     * @param balance                주문 가능 금액/수량
     * @param locked                 주문 중 묶여 있는 금액/수량
     * @param avg_buy_price          매수 평균가
     * @param avg_buy_price_modified 매수 평균가 수정 여부
     * @param unit_currency          평단가 기준 화폐
     */
    public record Response(
            String currency,
            double balance,
            double locked,
            double avg_buy_price,
            boolean avg_buy_price_modified,
            String unit_currency
    ) {}
}
