package me.marquez.upbit;

import me.marquez.upbit.entity.enums.OrderEnums;
import me.marquez.upbit.entity.exchange.orders.PostOrders;

import java.math.BigDecimal;

public class MarketPriceOrderTest {

    private static final String ACCESS = "";
    private static final String SECRET = "";

    public static void main(String[] args) {

        UpbitAPI.Exchange api = UpbitAPI.createExchangeAPI(ACCESS, SECRET);

        // 매수
//        PostOrders.Response response = api.postOrders(
//                PostOrders.Request.builder()
//                        .market("BTC-AUCTION")
//                        .side(OrderEnums.Side.BID)
//                        .price(new BigDecimal("0.00097319"))
//                        .ord_type(OrderEnums.OrderType.PRICE)
//                        .build()
//        );

        // 매도
//        PostOrders.Response response = api.postOrders(
//                PostOrders.Request.builder()
//                        .market("BTC-AUCTION")
//                        .side(OrderEnums.Side.ASK)
//                        .volume(new BigDecimal("2.77792367"))
//                        .ord_type(OrderEnums.OrderType.MARKET)
//                        .build()
//        );

//        System.out.println(response);

    }
}
