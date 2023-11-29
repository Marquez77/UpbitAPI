package me.marquez.upbit.entity.quotation.trades;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import me.marquez.upbit.entity.date.HourMinuteSecond;
import me.marquez.upbit.entity.date.YearMonthDay;
import me.marquez.upbit.entity.enums.OrderEnums;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class GetTradesTicks {

    /**
     * @param market    마켓 코드 (ex. KRW-BTC)
     * @param to        마지막 체결 시각. 비워서 요청시 가장 최근 데이터
     * @param count     체결 개수
     * @param cursor    페이지네이션 커서 (sequentialId)
     * @param daysAgo   최근 체결 날짜 기준 7일 이내의 이전 데이터 조회 가능. 비워서 요청 시 가장 최근 체결 날짜 반환. (범위: 1 ~ 7))
     */
    @Builder
    public record Request(
            @NonNull String market,
            @Nullable Date to,
            @Nullable int count,
            @Nullable String cursor,
            @Nullable int daysAgo
    ) {}

    /**
     * @param market                마켓 구분 코드
     * @param trade_date_utc        체결 일자(UTC 기준)
     * @param trade_time_utc        체결 시각(UTC 기준)
     * @param timestamp             체결 타임스탬프
     * @param trade_price           체결 가격
     * @param trade_volume          체결량
     * @param prev_closing_price    전일 종가(UTC 0시 기준)
     * @param change_price          변화량
     * @param ask_bid               매도/매수
     * @param sequential_id         체결 번호(Unique)
     */
    @ToString
    public record Response(
            String market,
            YearMonthDay trade_date_utc,
            HourMinuteSecond trade_time_utc,
            long timestamp,
            double trade_price,
            double trade_volume,
            double prev_closing_price,
            double change_price,
            OrderEnums.Side ask_bid,
            long sequential_id
    ) {}
}
