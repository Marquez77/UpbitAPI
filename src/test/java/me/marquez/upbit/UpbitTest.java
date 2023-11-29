package me.marquez.upbit;

import me.marquez.upbit.entity.exchange.accounts.GetAccounts;
import me.marquez.upbit.entity.quotation.market.GetMarketAll;

import java.util.Arrays;

public class UpbitTest {

    private static final String ACCESS = "xZoiIXZ0e7QoKBWaXrwDstWL8q7YTE8NOLsAvmp8";
    private static final String SECRET = "bi6eDGD04jVaJ74iOmyhd6PJQ7rGWrx0d3OSJXlX";

    public static void main(String[] args) {
        GetMarketAll.Response[] response = UpbitAPI.QUOTATION.getMarketAll(GetMarketAll.Request.builder().isDetails(false).build());
        System.out.println(Arrays.toString(response));

        UpbitAPI.Exchange api = UpbitAPI.createExchangeAPI(ACCESS, SECRET);
        GetAccounts.Response[] res = api.getAccounts();
        System.out.println(Arrays.toString(res));

    }
}
