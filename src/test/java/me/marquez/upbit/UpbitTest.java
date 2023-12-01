package me.marquez.upbit;

import me.marquez.upbit.entity.enums.OrderEnums;
import me.marquez.upbit.entity.enums.WithdrawEnums;
import me.marquez.upbit.entity.exchange.accounts.GetAccounts;
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

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

public class UpbitTest {

    private static final String ACCESS = "";
    private static final String SECRET = "";

    public static void main(String[] args) throws Exception {
//        GetMarketAll.Response[] response = UpbitAPI.QUOTATION.getMarketAll(GetMarketAll.Request.builder().isDetails(true).build());
//        System.out.println(Arrays.toString(response));

//        GetCandlesMinutes.Response[] response = UpbitAPI.QUOTATION.getCandlesMinutes(
//                GetCandlesMinutes.Unit.MINUTE,
//                GetCandlesMinutes.Request.builder()
//                        .market("KRW-BTC")
//                        .to(OffsetDateTime.now())
//                        .count(5)
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetCandlesDays.Response[] response = UpbitAPI.QUOTATION.getCandlesDays(
//                GetCandlesDays.Request.builder()
//                        .market("BTC-AUCTION")
//                        .to(OffsetDateTime.now())
//                        .count(5)
//                        .convertingPriceUnit("KRW")
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetCandlesWeeks.Response[] response = UpbitAPI.QUOTATION.getCandlesWeeks(
//                GetCandlesWeeks.Request.builder()
//                        .market("BTC-AUCTION")
//                        .to(OffsetDateTime.now())
//                        .count(5)
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetCandlesMonths.Response[] response = UpbitAPI.QUOTATION.getCandlesMonths(
//                GetCandlesMonths.Request.builder()
//                        .market("BTC-AUCTION")
//                        .to(OffsetDateTime.now())
//                        .count(5)
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetTradesTicks.Response[] response = UpbitAPI.QUOTATION.getTradesTicks(
//                GetTradesTicks.Request.builder()
//                        .market("KRW-BTC")
//                        .to(LocalTime.now())
//                        .count(3)
//                        .cursor("1701251040688004")
//                        .daysAgo(1)
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetTicker.Response[] response = UpbitAPI.QUOTATION.getTicker(
//                GetTicker.Request.builder()
//                        .markets("KRW-BTC,KRW-ETH")
//                        .build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetOrderBook.Response[] response = UpbitAPI.QUOTATION.getOrderBook(
//                GetOrderBook.Request.builder()
//                        .markets(new String[] { "KRW-BTC", "KRW-ETH" })
//                        .build()
//        );
//        for (GetOrderBook.Response res : response) {
//            System.out.println(res.market());
//            System.out.println(res.timestamp());
//            System.out.println(res.total_ask_size());
//            System.out.println(res.total_bid_size());
//            System.out.println(Arrays.toString(res.orderbook_units()));
//        }


        UpbitAPI.Exchange api = UpbitAPI.createExchangeAPI(ACCESS, SECRET);
//
//        GetAccounts.Response[] response = api.getAccounts();
//        System.out.println(Arrays.toString(response));

//        GetOrdersChance.Response response = api.getOrdersChance(
//                GetOrdersChance.Request.builder()
//                        .market("KRW-BTC")
//                        .build()
//        );
//        System.out.println(response.bid_fee());
//        System.out.println(response.ask_fee());
//        System.out.println(response.market().id() + " " + response.market().name() + " " + response.market().max_total() + " " + response.market().state());
//        System.out.println(response.market().ask());
//        System.out.println(response.market().bid());
//        System.out.println(Arrays.toString(response.market().ask_types()));
//        System.out.println(Arrays.toString(response.market().bid_types()));
//        System.out.println(Arrays.toString(response.market().order_sides()));
//        System.out.println(response.bid_account());
//        System.out.println(response.ask_account());

//        GetOrder.Response response = api.getOrder(
//                GetOrder.Request.builder()
//                        .uuid(UUID.fromString("850e48bc-ed59-4d31-a6c0-cf347290402b"))
//                        .build()
//        );
//        System.out.println(response);

        GetOrders.Response[] response = api.getOrders(
                GetOrders.Request.builder()
                        .market("KRW-BTC")
                        .state(OrderEnums.State.DONE)
                        .build()
        );
        System.out.println(Arrays.toString(response));

//        DeleteOrder.Response response = api.deleteOrder(
//                DeleteOrder.Request.builder()
//                        .uuid(UUID.fromString("f5ed540b-0566-4484-a80c-ba25b273c599"))
//                        .build()
//        );
//        System.out.println(response);

//        PostOrders.Response response = api.postOrders(
//                PostOrders.Request.builder()
//                        .market("KRW-BTC")
//                        .side(OrderEnums.Side.BID)
//                        .volume(new BigDecimal("0.0002"))
//                        .price(new BigDecimal("50000000"))
//                        .ord_type(OrderEnums.OrderType.LIMIT)
//                        .build()
//        );
//        System.out.println(response);
//        PostOrders.Response response = api.postOrders(
//                PostOrders.Request.builder()
//                        .market("BTC-AUCTION")
//                        .side(OrderEnums.Side.BID)
//                        .volume(new BigDecimal("1.62846327"))
//                        .price(new BigDecimal("0.00036300"))
//                        .ord_type(OrderEnums.OrderType.LIMIT)
//                        .build()
//        );
//        System.out.println(response);

//        GetWithdraws.Response[] response = api.getWithdraws(
//                GetWithdraws.Request.builder().build()
//        );
//        System.out.println(Arrays.toString(response));

//        GetWithdraw.Response response = api.getWithdraw(
//                GetWithdraw.Request.builder()
//                        .uuid(UUID.fromString("80711eb6-fd79-4920-bd28-ff32e0a1b09d"))
//                        .build()
//        );
//        System.out.println(response);

//        GetWithdrawsChance.Response response = api.getWithdrawsChance(
//                GetWithdrawsChance.Request.builder()
//                        .currency("MBL")
//                        .net_type("ONT")
//                        .build()
//        );
//        System.out.println(response);

//        GetWithdrawsCoinAddresses.Response[] response = api.getWithdrawsCoinAddresses();
//        System.out.println(Arrays.toString(response));

//        PostWithdrawsCoin.Response response = api.postWithdrawsCoin(
//                PostWithdrawsCoin.Request.builder()
//                        .currency("MBL")
//                        .net_type("ONT")
//                        .amount(new BigDecimal("172"))
//                        .address("AQfs7bJQYfrG28jWb6dMMYktsSaWY5m2h8")
//                        .transaction_type(WithdrawEnums.TransactionType.INTERNAL)
//                        .build()
//        );
//        System.out.println(response);

//        PostWithdrawsKRW.Response response = api.postWithdrawsKRW(
//                PostWithdrawsKRW.Request.builder()
//                        .amount(new BigDecimal("10000"))
//                        .two_factor_type(PostWithdrawsKRW.Request.TwoFactorType.NAVER)
//                        .build()
//        );
//        System.out.println(response);

        System.exit(0);
    }
}
