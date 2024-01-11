package me.marquez.upbit.entity.exchange.deposits;

import org.jetbrains.annotations.Nullable;

/**
 * 입금 주소 생성 요청
 * 입금 주소 생성을 요청한다.
 *
 * 입금 주소 생성 요청 API 유의사항
 * 입금 주소의 생성은 서버에서 비동기적으로 이뤄집니다.
 * 비동기적 생성 특성상 요청과 동시에 입금 주소가 발급되지 않을 수 있습니다.
 * 주소 발급 요청 시 결과로 Response1이 반환되며 주소 발급 완료 이전까지 계속 Response1이 반환됩니다.
 * 주소가 발급된 이후부터는 새로운 주소가 발급되는 것이 아닌 이전에 발급된 주소가 Response2 형태로 반환됩니다.
 * 정상적으로 주소가 생성되지 않는다면 일정 시간 이후 해당 API를 다시 호출해주시길 부탁드립니다.
 */
public class PostDepositsGenerateCoinAddress {

    public static final String END_POINT = "v1/deposits/generate_coin_address";

    /**
     * @param currency Currency 코드
     * @param net_type 입금 네트워크
     */
    public record Request(
            String currency,
            String net_type
    ) {}

    /**
     * @param success            요청 성공 여부
     * @param message            요청 결과 메시지
     *
     * @param currency           화폐를 의미하는 영문 대문자 코드
     * @param net_type           입금 네트워크
     * @param deposit_address    입금 주소
     * @param secondary_address  2차 입금 주소
     */
    public record Response(
            @Nullable boolean success,
            @Nullable String message,

            @Nullable String currency,
            @Nullable String net_type,
            @Nullable String deposit_address,
            @Nullable String secondary_address
    ) {}

}
