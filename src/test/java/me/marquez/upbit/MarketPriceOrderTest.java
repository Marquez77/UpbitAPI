package me.marquez.upbit;

import me.marquez.upbit.entity.enums.OrderEnums;
import me.marquez.upbit.entity.exchange.orders.GetOrder;
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
//                        .market("BTC-INJ")
//                        .side(OrderEnums.Side.ASK)
//                        .volume(new BigDecimal("1.30361914"))
//                        .ord_type(OrderEnums.OrderType.MARKET)
//                        .build()
//        );
        var response = api.getOrder(
                GetOrder.Request.builder()
                        .uuid("1758e8da-95e3-4ae3-9d9d-14617f49fae7")
                        .build()
        );

        System.out.println(response);

    }
}
