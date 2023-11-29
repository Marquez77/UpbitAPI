package me.marquez.upbit.entity.quotation.candles;

import lombok.Builder;
import lombok.NonNull;
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
            @NonNull int count,
            @Nullable String convertingPriceUnit
    ) {}

    public record Response(
            String market,
            String candle_date_time_utc,
            String candle_date_time_kst,
    )
}
