package me.marquez.upbit.entity.exchange.accounts;

import lombok.ToString;

import java.math.BigDecimal;

/**
 * 전체 계좌 조회
 * 내가 보유한 자산 리스트를 보여줍니다.
 */
public class GetAccounts {

    public static final String END_POINT = "v1/accounts";

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
            BigDecimal balance,
            BigDecimal locked,
            BigDecimal avg_buy_price,
            boolean avg_buy_price_modified,
            String unit_currency
    ) {}
}
