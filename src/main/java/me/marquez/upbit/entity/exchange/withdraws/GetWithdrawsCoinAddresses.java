package me.marquez.upbit.entity.exchange.withdraws;

import lombok.ToString;

/**
 * 출금 허용 주소 리스트 조회
 * 등록된 출금 허용 주소 목록을 조회한다.
 *
 * 출금 기능을 이용하기 위해서는 주소 등록이 필요합니다.
 * Open API를 통해 디지털 자산을 출금하기 위해서는 출금 허용 주소 등록이 필요합니다.
 * 등록은 업비트 웹 > [MY] > [Open API 관리] > [디지털 자산 출금주소 관리] 페이지에서 진행하실 수 있습니다.
 */
public class GetWithdrawsCoinAddresses {

    public static final String END_POINT = "v1/withdraws/coin_addresses";

    /**
     * 네트워크 타입(net_type) vs. 네트워크 명(network_name)
     *
     * 네트워크 타입(net_type)이란?
     * 디지털 자산 입출금에 활용되는 블록체인 네트워크를 뜻하며, 디지털 자산의 종류에 따라 활용되는 네트워크(체인)이 다를 수 있습니다. *네트워크가 일치하지 않는 경우 정상 입출금이 어려울 수 있으니 사용하는 주소와 네트워크가 정확히 일치하는지 확인해 주세요.
     *
     * 네트워크 명(network_name)이란?
     * 업비트에서 지원하고 있는 디지털 자산의 블록체인 네트워크 명을 확인할 수 있는 기능입니다.
     * @param currency              출금 화폐
     * @param net_type              출금 네트워크 타입
     * @param network_name          출금 네트워크 이름
     * @param withdraw_address      출금 주소
     * @param secondary_address     2차 출금 주소 (필요한 디지털 자산에 한해서)
     */
    public record Response(
            String currency,
            String net_type,
            String network_name,
            String withdraw_address,
            String secondary_address
    ) {}
}
