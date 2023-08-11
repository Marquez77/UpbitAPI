package me.marquez.upbit.entity;

import java.util.Date;

public class PostOrders {
    /**
     * @param uuid
     * @param side
     * @param ord_type
     * @param price
     * @param state
     * @param market
     * @param crated_at
     * @param volume
     * @param remaining_volume
     * @param reserved_fee
     * @param remaining_fee
     * @param paid_fee
     * @param locked
     * @param executed_volume
     * @param trades_count
     */
    public record Response(
            String uuid,
            String side,
            String ord_type,
            double price,
            String state,
            String market,
            Date crated_at,
            double volume,
            double remaining_volume,
            double reserved_fee,
            double remaining_fee,
            double paid_fee,
            double locked,
            double executed_volume,
            int trades_count
    ) {}
}
