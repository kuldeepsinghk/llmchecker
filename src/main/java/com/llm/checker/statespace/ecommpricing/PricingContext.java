package com.llm.checker.statespace.ecommpricing;

public class PricingContext {

    public final boolean isPremiumCustomer;
    public final boolean isFirstPurchase;
    public final boolean hasCoupon;
    public final boolean isGroceryItem;
    public final Region region;
    public final double cartValue;

    public PricingContext(
            boolean isPremiumCustomer,
            boolean isFirstPurchase,
            boolean hasCoupon,
            boolean isGroceryItem,
            Region region,
            double cartValue
    ) {
        this.isPremiumCustomer = isPremiumCustomer;
        this.isFirstPurchase = isFirstPurchase;
        this.hasCoupon = hasCoupon;
        this.isGroceryItem = isGroceryItem;
        this.region = region;
        this.cartValue = cartValue;
    }
}

