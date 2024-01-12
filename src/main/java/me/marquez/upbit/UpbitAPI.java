package me.marquez.upbit;

import me.marquez.upbit.entity.exchange.accounts.GetAccounts;
import me.marquez.upbit.entity.exchange.deposits.*;
import me.marquez.upbit.entity.exchange.orders.*;
import me.marquez.upbit.entity.exchange.withdraws.*;
import me.marquez.upbit.entity.quotation.GetOrderBook;
import me.marquez.upbit.entity.quotation.GetTicker;
import me.marquez.upbit.entity.quotation.candles.GetCandlesDays;
import me.marquez.upbit.entity.quotation.candles.GetCandlesMinutes;
import me.marquez.upbit.entity.quotation.candles.GetCandlesMonths;
import me.marquez.upbit.entity.quotation.candles.GetCandlesWeeks;
import me.marquez.upbit.entity.quotation.market.GetMarketAll;
import me.marquez.upbit.entity.quotation.trades.GetTradesTicks;
import me.marquez.upbit.exception.UpbitAPIException;
import me.marquez.upbit.internal.UpbitCore;
import org.jetbrains.annotations.Nullable;

public class UpbitAPI {

    public static Exchange createExchangeAPI(String accessKey, String secretKey) {
        return new UpbitCore(accessKey, secretKey);
    }

    // TODO: 2023-12-01  RateLimit 계산을 위해 추후 아이피로 생성할 수 있도록 변경해야 함
    public static final Quotation QUOTATION = new UpbitCore();

    /**
	=============================================
					EXCHANGE API
			[주문 요청] 초당 8회, 분당 200회
		   [주문 요청 외] 초당 30회, 분당 900회
	=============================================
	*/
    public interface Exchange {
        /**
         * 전체 계좌 조회
         * 내가 보유한 자산 리스트를 보여줍니다.
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetAccounts.Response[] getAccounts() throws UpbitAPIException;

        /**
         * 주문 가능 정보
         * 마켓별 주문 가능 정보를 확인한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetOrdersChance.Response getOrdersChance(GetOrdersChance.Request request) throws UpbitAPIException;

        /**
         * 개별 주문 조회
         * 주문 UUID 를 통해 개별 주문 건을 조회한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetOrder.Response getOrder(GetOrder.Request request) throws UpbitAPIException;

        /**
         * 주문 리스트 조회
         * 주문 리스트를 조회한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetOrders.Response[] getOrders(GetOrders.Request request) throws UpbitAPIException;

        /**
         * 주문 취소 접수
         * 주문 UUID를 통해 해당 주문에 대한 취소 접수를 한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable DeleteOrder.Response deleteOrder(DeleteOrder.Request request) throws UpbitAPIException;

        /**
         * 주문하기
         * 주문 요청을 한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable PostOrders.Response postOrders(PostOrders.Request request) throws UpbitAPIException;

        /**
         * 출금 리스트 조회
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetWithdraws.Response[] getWithdraws(GetWithdraws.Request request) throws UpbitAPIException;

        /**
         * 개별 출금 조회
         * 출금 UUID를 통해 개별 출금 정보를 조회한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetWithdraw.Response getWithdraw(GetWithdraw.Request request) throws UpbitAPIException;

        /**
         * 출금 가능 정보
         * 해당 통화의 가능한 출금 정보를 확인한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetWithdrawsChance.Response getWithdrawsChance(GetWithdrawsChance.Request request) throws UpbitAPIException;

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
         *
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable PostWithdrawsCoin.Response postWithdrawsCoin(PostWithdrawsCoin.Request request) throws UpbitAPIException;

        /**
         * 원화 출금하기
         * 원화 출금을 요청한다. 등록된 출금 계좌로 출금된다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable PostWithdrawsKRW.Response postWithdrawsKRW(PostWithdrawsKRW.Request request) throws UpbitAPIException;

        /**
         * 출금 허용 주소 리스트 조회
         * 등록된 출금 허용 주소 목록을 조회한다.
         *
         * 출금 기능을 이용하기 위해서는 주소 등록이 필요합니다.
         * Open API를 통해 디지털 자산을 출금하기 위해서는 출금 허용 주소 등록이 필요합니다.
         * 등록은 업비트 웹 > [MY] > [Open API 관리] > [디지털 자산 출금주소 관리] 페이지에서 진행하실 수 있습니다.
         *
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetWithdrawsCoinAddresses.Response[] getWithdrawsCoinAddresses() throws UpbitAPIException;

        /**
         * 개별 입금 조회
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetDeposit.Response getDeposit(GetDeposit.Request request) throws UpbitAPIException;

        /**
         * 입금 리스트 조회
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetDeposits.Response[] getDeposits(GetDeposits.Request request) throws UpbitAPIException;

        /**
         * 개별 입금 주소 조회
         *
         * 입금 주소 조회 요청 API 유의사항
         * 입금 주소 생성 요청 이후 아직 발급되지 않은 상태일 경우 deposit_address가 null일 수 있습니다.
         *
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetDepositsCoinAddress.Response getDepositsCoinAddress(GetDepositsCoinAddress.Request request) throws UpbitAPIException;

        /**
         * 전체 입금 주소 조회
         *
         * 입금 주소 조회 요청 API 유의사항
         * 입금 주소 생성 요청 이후 아직 발급되지 않은 상태일 경우 deposit_address가 null일 수 있습니다.
         *
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetDepositsCoinAddresses.Response[] getDepositsCoinAddresses() throws UpbitAPIException;

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
         *
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable Object postDepositsGenerateCoinAddress(PostDepositsGenerateCoinAddress.Request request) throws UpbitAPIException;

        /**
         * 원화 입금하기
         * 원화 입금을 요청한다.
         *
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable PostDepositsKRW.Response postDepositsKRW(PostDepositsKRW.Request request) throws UpbitAPIException;

    }

    /**
	=============================================
					QUOTATION API
				 초당 10회, 분당 600회
				 		IP 기준
	=============================================
	*/
    public interface Quotation {
        /**
         * 마켓 코드 조회
         * 업비트에서 거래 가능한 마켓 목록
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetMarketAll.Response[] getMarketAll(GetMarketAll.Request request) throws UpbitAPIException;

        /**
         * 분(Minute) 캔들
         * @param unit      단위
         * @param request   요청
         * @return          요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetCandlesMinutes.Response[] getCandlesMinutes(GetCandlesMinutes.Unit unit, GetCandlesMinutes.Request request) throws UpbitAPIException;

        /**
         * 일(Day) 캔들
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetCandlesDays.Response[] getCandlesDays(GetCandlesDays.Request request) throws UpbitAPIException;

        /**
         * 주(Week) 캔들
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetCandlesWeeks.Response[] getCandlesWeeks(GetCandlesWeeks.Request request) throws UpbitAPIException;

        /**
         * 월(Month) 캔들
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetCandlesMonths.Response[] getCandlesMonths(GetCandlesMonths.Request request) throws UpbitAPIException;

        /**
         * 최근 체결 내역
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetTradesTicks.Response[] getTradesTicks(GetTradesTicks.Request request) throws UpbitAPIException;

        /**
         * 현재가 정보
         * 요청 당시 종목의 스냅샷을 반환한다.
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetTicker.Response[] getTicker(GetTicker.Request request) throws UpbitAPIException;

        /**
         * 호가 정보 조회
         * @param request 요청
         * @return 요청 결과 (요청 수가 제한되었을 경우 null 반환)
         */
        @Nullable GetOrderBook.Response[] getOrderBook(GetOrderBook.Request request) throws UpbitAPIException;
    }


}
