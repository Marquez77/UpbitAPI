package me.marquez.upbit;

import me.marquez.upbit.entity.quotation.market.GetMarketAll;

import java.util.Arrays;

public class RateLimitTest {

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 100; i++) {
            long start = System.currentTimeMillis();
            GetMarketAll.Response[] response = UpbitAPI.QUOTATION.getMarketAll(GetMarketAll.Request.builder().isDetails(true).build());
            long ms = System.currentTimeMillis()-start;
            System.out.println(i + " " + ms + "ms " + Arrays.toString(response));
            if(response == null) i--;
            Thread.sleep(Math.max(0, 50-ms));
        }
    }

}
