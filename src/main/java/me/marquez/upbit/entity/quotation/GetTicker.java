package me.marquez.upbit.entity.quotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import me.marquez.upbit.entity.date.HourMinuteSecond;
import me.marquez.upbit.entity.date.YearMonthDay;

/**
 * 현재가 정보
 * 요청 당시 종목의 스냅샷을 반환한다.
 */
public class GetTicker {

    public static final String END_POINT = "v1/ticker";

    /**
     * @param markets   반점으로 구분되는 마켓 코드 (ex. KRW-BTC, BTC-ETH)
     */
    @Builder
    public record Request(
            @NonNull String[] markets
    ) {}

    /**
     * @param market                    종목 구분 코드
     * @param trade_date                최근 거래 일자(UTC)
     * @param trade_time                최근 거래 시각(UTC)
     * @param trade_date_kst            최근 거래 일자(KST)
     * @param trade_time_kst            최근 거래 시각(KST)
     * @param trade_timestamp           최근 거래 일시(UTC)
     * @param opening_price             시가
     * @param high_price                고가
     * @param low_price                 저가
     * @param trade_price               종가(현재가)
     * @param prev_closing_price        전일 종가(UTC 0시 기준)
     * @param change                    보합, 상승, 하락
     * @param change_price              변화액의 절대값
     * @param change_rate               변화율의 절대값
     * @param signed_change_price       부호가 있는 변화액
     * @param signed_change_rate        부호가 있는 변화율
     * @param trade_volume              가장 최근 거래량
     * @param acc_trade_price           누적 거래대금(UTC 0시 기준)
     * @param acc_trade_price_24h       24시간 누적 거래대금
     * @param acc_trade_volume          누적 거래량(UTC 0시 기준)
     * @param acc_trade_volume_24h      24시간 누적 거래량
     * @param highest_52_week_price     52주 신고가
     * @param highest_52_week_date      52주 신고가 달성일
     * @param lowest_52_week_price      52주 신저가
     * @param lowest_52_week_date       52주 신저가 달성일
     * @param timestamp                 타임스탬프
     *
     * 위 응답의 change, change_price, change_rate, signed_change_price, signed_change_rate 필드들은 전일종가 대비 값입니다.
     */
    public record Response(
            String market,
            YearMonthDay trade_date,
            HourMinuteSecond trade_time,
            YearMonthDay trade_date_kst,
            HourMinuteSecond trade_time_kst,
            long trade_timestamp,
            double opening_price,
            double high_price,
            double low_price,
            double trade_price,
            double prev_closing_price,
            Change change,
            double change_price,
            double change_rate,
            double signed_change_price,
            double signed_change_rate,
            double trade_volume,
            double acc_trade_price,
            double acc_trade_price_24h,
            double acc_trade_volume,
            double acc_trade_volume_24h,
            double highest_52_week_price,
            YearMonthDay highest_52_week_date,
            double lowest_52_week_price,
            YearMonthDay lowest_52_week_date,
            long timestamp
    ) {
        @AllArgsConstructor
        public enum Change {
            /**
             * 보합
             */
            EVEN("EVEN"),

            /**
             * 상승
             */
            RISE("RISE"),

            /**
             * 하락
             */
            FALL("FALL");

            private final String change;
        }
    }
}
