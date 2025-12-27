package com.llm.checker.statespace.ecommpricing;

public class PricingOracle {

    public static PricingResult expected(PricingContext c) {

        double discount = 0.0;

        // Grocery blocks all percentage discounts
        if (!c.isGroceryItem) {

            if (c.isPremiumCustomer) {
                discount += 10.0;
            }

            if (c.isFirstPurchase && !c.hasCoupon) {
                discount += 5.0;
            }

            if (c.hasCoupon) {
                discount += 7.0;
            }
        }

        // EU cap
        if (c.region == Region.EU) {
            discount = Math.min(discount, 15.0);
        }

        // Free shipping
        boolean freeShipping =
                c.cartValue >= 100.0 &&
                        !c.hasCoupon;

        return new PricingResult(discount, freeShipping);
    }
}

