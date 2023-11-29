package me.marquez.upbit.entity.quotation.candles;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class GetCandlesDays {

    /**
     * @param market                마켓 코드 (ex. KRW-BTC)
     * @param to                    마지막 캔들 시각 (exclusive). 비워서 요청시 가장 최근 캔들
     * @param count                 캔들 개수(최대 200개까지 요청 가능)
     * @param convertingPriceUnit   종가 환산 화폐 단위 (생략 가능, KRW로 명시할 시 원화 환산 가격을 반환.)
     */
    @Builder
    public record Request(
            @NonNull String market,
            @Nullable Date to,
            @Nullable int count,
            @Nullable String convertingPriceUnit
    ) {}

    /**
     * @param market                    마켓명
     * @param candle_date_time_utc      캔들 기준 시각(UTC 기준)
     * @param candle_date_time_kst      캔들 기준 시각(KST 기준)
     * @param opening_price             시가
     * @param high_price                고가
     * @param low_price                 저가
     * @param trade_price               종가
     * @param timestamp                 마지막 틱이 저장된 시각
     * @param candle_acc_trade_price    누적 거래 금액
     * @param candle_acc_trade_volume   누적 거래량
     * @param prev_closing_price        전일 종가(UTC 0시 기준)
     * @param change_price              전일 종가 대비 변화 금액
     * @param change_rate               전일 종가 대비 변화량
     * @param converted_trade_price     종가 환산 화폐 단위로 환산된 가격 (요청에 convertingPriceUnit 파라미터 없을 시 해당 필드 포함되지 않음.)
     */
    @ToString
    public record Response(
            String market,
            Date candle_date_time_utc,
            Date candle_date_time_kst,
            double opening_price,
            double high_price,
            double low_price,
            double trade_price,
            long timestamp,
            double candle_acc_trade_price,
            double candle_acc_trade_volume,
            double prev_closing_price,
            double change_price,
            double change_rate,
            double converted_trade_price
    ) {}
}
