package me.marquez.upbit.entity.exchange.withdraws;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import me.marquez.upbit.entity.enums.WithdrawEnums;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * 디지털 자산 출금하기
 * 디지털 자산 출금을 요청한다.
 *
 * 허용된 지갑 주소로만 출금을 진행할 수 있습니다.
 * Open API를 이용하여 출금을 진행하기 위해서는 출금허용주소 등록이 필요합니다. 다음 페이지에서 출금 주소를 등록해주시길 바랍니다.
 * 업비트 웹 [마이페이지] > [Open API 관리] > [디지털 자산 출금주소 관리] 탭
 *
 * 업비트 회원의 지갑 주소로만 바로출금을 진행할 수 있습니다.
 * 업비트 회원의 주소가 아닌 주소로 바로출금을 요청하는 경우, 출금이 정상적으로 수행되지 않습니다. 반드시 주소를 확인 후 출금을 진행하시기 바랍니다.
 */
public class PostWithdrawsCoin {

    public static final String END_POINT = "v1/withdraws/coin";

    /**
     * @param currency              Currency 코드
     * @param net_type              출금 네트워크
     * @param amount                출금 수량
     * @param address               출금 가능 주소에 등록된 출금 주소
     * @param secondary_address     2차 출금 주소 (필요한 코인에 한해서)
     * @param transaction_type      출금 유형
     */
    @Builder
    @ToString
    public record Request(
            @NonNull String currency,
            @NonNull String net_type,
            @NonNull double amount,
            @NonNull String address,
            @Nullable String secondary_address,
            @Nullable WithdrawEnums.TransactionType transaction_type
    ) {}

    /**
     * @param type              입출금 종류
     * @param uuid              출금의 고유 아이디
     * @param currency          화폐를 의미하는 영문 대문자 코드
     * @param net_type          출금 네트워크
     * @param txid              출금의 트랜잭션 아이디
     * @param state             출금 상태
     * @param created_at        출금 생성 시간
     * @param done_at           출금 완료 시간
     * @param amount            출금 금액/수량
     * @param fee               출금 수수료
     * @param krw_amount        원화 환산 가격
     * @param transaction_type  출금 유형
     */
    @ToString
    public record Response(
            String type,
            UUID uuid,
            String currency,
            String net_type,
            String txid,
            WithdrawEnums.State state,
            Date created_at,
            Date done_at,
            double amount,
            double fee,
            double krw_amount,
            WithdrawEnums.TransactionType transaction_type
    ) {}
}
