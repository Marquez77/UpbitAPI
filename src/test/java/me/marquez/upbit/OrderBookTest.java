package me.marquez.upbit;

import me.marquez.upbit.entity.quotation.GetOrderBook;
import me.marquez.upbit.entity.quotation.market.GetMarketAll;

import java.util.Arrays;

public class OrderBookTest {

    public static void main(String[] args) {
        String[] markets = Arrays.stream(UpbitAPI.QUOTATION.getMarketAll(GetMarketAll.Request.builder().build())).map(GetMarketAll.Response::market).filter(s -> s.startsWith("BTC")).toArray(String[]::new);
        System.out.println(Arrays.toString(markets));
        var result = UpbitAPI.QUOTATION.getOrderBook(GetOrderBook.Request.builder().markets(markets).build());
        System.out.println(Arrays.toString(result));
        System.exit(0);
    }

}
