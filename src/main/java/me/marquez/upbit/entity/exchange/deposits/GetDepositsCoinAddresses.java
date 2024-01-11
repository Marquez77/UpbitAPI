package me.marquez.upbit.entity.exchange.deposits;

/**
 * 전체 입금 주소 조회
 */
public class GetDepositsCoinAddresses {

    public static final String END_POINT = "v1/deposits/coin_addresses";

    /**
     * @param currency           화폐를 의미하는 영문 대문자 코드
     * @param net_type           입금 네트워크
     * @param deposit_address    입금 주소
     * @param secondary_address  2차 입금 주소
     */
    public record Response(
            String currency,
            String net_type,
            String deposit_address,
            String secondary_address
    ) {}

}
