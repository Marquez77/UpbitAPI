package me.marquez.upbit.entity.quotation.candles;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * 일(Day) 캔들
 */
public class GetCandlesDays {

    public static final String END_POINT = "v1/candles/days";

    /**
     * @param market                마켓 코드 (ex. KRW-BTC)
     * @param to                    마지막 캔들 시각 (exclusive). 비워서 요청시 가장 최근 캔들
     * @param count                 캔들 개수(최대 200개까지 요청 가능)
     * @param convertingPriceUnit   종가 환산 화폐 단위 (생략 가능, KRW로 명시할 시 원화 환산 가격을 반환.)
     *                              convertingPriceUnit 파라미터의 경우, 원화 마켓이 아닌 다른 마켓(ex. BTC, ETH)의 일봉 요청시 종가를 명시된 파라미터 값으로 환산해 converted_trade_price 필드에 추가하여 반환합니다.
     *                              현재는 원화(KRW) 로 변환하는 기능만 제공하며 추후 기능을 확장할 수 있습니다.
     */
    @Builder
    public record Request(
            @NonNull String market,
            @Nullable OffsetDateTime to,
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
            BigDecimal prev_closing_price,
            BigDecimal change_price,
            BigDecimal change_rate,
            @Nullable Double converted_trade_price
    ) {}
}
