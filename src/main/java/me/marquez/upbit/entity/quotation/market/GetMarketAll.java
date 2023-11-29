package me.marquez.upbit.entity.quotation.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

/**
 * 마켓 코드 조회
 * 업비트에서 거래 가능한 마켓 목록
 */
public class GetMarketAll {

    public static final String END_POINT = "v1/market/all";

    /**
     * @param isDetails 유의종목 필드과 같은 상세 정보 노출 여부(선택 파라미터)
     */
    @Builder
    public record Request(
            @Nullable boolean isDetails
    ) {}

    /**
     * @param market            업비트에서 제공중인 시장 정보
     * @param korean_name       거래 대상 디지털 자산 한글명
     * @param english_name      거래 대상 디지털 자산 영문명
     * @param market_warning    유의 종목 여부
     *                          '유의 종목' 지정 여부에 대해 반환하며, '주의 종목' 지정 여부는 지원하지 않습니다.
     */
    public record Response(
            @NonNull String market,
            @NonNull String korean_name,
            @NonNull String english_name,
            @Nullable MarketWarning market_warning
    ) {
        @AllArgsConstructor
        public enum MarketWarning {
            /**
             * NONE (해당 사항 없음)
             */
            NONE("NONE"),

            /**
             * CAUTION (투자유의)
             */
            CAUTION("CAUTION");

            private final String market_warning;
        }
    }
}
