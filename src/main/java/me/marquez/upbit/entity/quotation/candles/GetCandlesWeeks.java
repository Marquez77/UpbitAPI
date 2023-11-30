package me.marquez.upbit.entity.quotation.candles;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * 주(Week) 캔들
 */
public class GetCandlesWeeks {

    public static final String END_POINT = "v1/candles/weeks";

    /**
     * @param market    마켓 코드 (ex. KRW-BTC)
     * @param to        마지막 캔들 시각 (exclusive). 비워서 요청시 가장 최근 캔들
     * @param count     캔들 개수(최대 200개까지 요청 가능)
     */
    @Builder
    public record Request(
            @NonNull String market,
            @Nullable OffsetDateTime to,
            @Nullable int count
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
     * @param first_day_of_period       캔들 기간의 가장 첫 날
     */
    public record Response(
            String market,
            LocalDateTime candle_date_time_utc,
            LocalDateTime candle_date_time_kst,
            BigDecimal opening_price,
            BigDecimal high_price,
            BigDecimal low_price,
            BigDecimal trade_price,
            long timestamp,
            BigDecimal candle_acc_trade_price,
            BigDecimal candle_acc_trade_volume,
            LocalDate first_day_of_period
    ) {}
}
