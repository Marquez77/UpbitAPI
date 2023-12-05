package me.marquez.upbit;

import me.marquez.upbit.entity.enums.OrderEnums;
import me.marquez.upbit.entity.exchange.orders.DeleteOrder;
import me.marquez.upbit.entity.exchange.orders.GetOrder;
import me.marquez.upbit.entity.exchange.orders.PostOrders;

import java.math.BigDecimal;

public class OrderCancelTest {

    public static void main(String[] args) throws InterruptedException {
        var api = UpbitAPI.createExchangeAPI("", "");

        var postOrder = api.postOrders(
                PostOrders.Request.builder()
                        .market("KRW-BTC")
                        .side(OrderEnums.Side.ASK)
                        .ord_type(OrderEnums.OrderType.MARKET)
                        .volume(new BigDecimal("0.0005"))
                        .build()
        );

        System.out.println(postOrder.uuid());

        for(int i = 0; i < 20; i++) {
            var getOrder = api.getOrder(
                    GetOrder.Request.builder()
                            .uuid(postOrder.uuid())
                            .build()
            );
            if(getOrder == null)
                continue;
            System.out.println(getOrder);
            if(getOrder.state() == OrderEnums.State.CANCEL || getOrder.state() == OrderEnums.State.DONE) {
                var deleteOrder = api.deleteOrder(
                        DeleteOrder.Request.builder()
                                .uuid(postOrder.uuid())
                                .build()
                );
                System.out.println(deleteOrder);
            }
            Thread.sleep(200);
        }

    }

}
