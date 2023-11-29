package me.marquez.upbit;

import me.marquez.upbit.entity.exchange.accounts.GetAccounts;

public class UpbitAPI {

    public static Exchange createExchangeAPI(String accessKey, String secretKey) {
        return new UpbitRestAPI(accessKey, secretKey);
    }

    public static final Quotation QUOTATION = new UpbitRestAPI();

    public interface Exchange {
        GetAccounts.Response getAccounts();
    }

    public interface Quotation {

    }


}
