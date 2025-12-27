package com.llm.checker.statespace.ecommpricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PricingInvariantTest {

    private final PricingService pricingService = new YourPricingServiceImplementation();

    @Test
    void pricingMustRespectBasicSanity() {
        for (PricingContext c : PricingStateGenerator.allStates()) {
            PricingResult r = pricingService.price(c);
            assertBasicSanity(r);
        }
    }
    @Test
    void pricingMustRespectGroceryInvariant() {
        for (PricingContext c : PricingStateGenerator.allStates()) {
            PricingResult r = pricingService.price(c);
            assertGroceryInvariants(c, r);
        }
    }
    @Test
    void pricingMustRespectCouponInvariant() {
        for (PricingContext c : PricingStateGenerator.allStates()) {
            PricingResult r = pricingService.price(c);
            assertCouponInvariants(c, r);
        }
    }
    @Test
    void pricingMustRespectEUInvariant() {
        for (PricingContext c : PricingStateGenerator.allStates()) {
            PricingResult r = pricingService.price(c);
            assertCouponInvariants(c, r);
        }
    }

    @Test
    void pricingMustRespectFreeShippingInvariant() {
        for (PricingContext c : PricingStateGenerator.allStates()) {
            PricingResult r = pricingService.price(c);
            assertFreeShippingInvariants(c, r);
        }
    }

    private void assertBasicSanity(PricingResult r) {

        assertTrue(r.discountPercentage >= 0.0,
                "Discount must not be negative");

        assertTrue(r.discountPercentage <= 100.0,
                "Discount must not exceed 100%");
    }

    private void assertGroceryInvariants(PricingContext c, PricingResult r) {

        if (c.isGroceryItem) {
            assertEquals(0.0, r.discountPercentage,
                    "Grocery items must block all percentage discounts");
        }
    }

    private void assertCouponInvariants(PricingContext c, PricingResult r) {

        if (c.hasCoupon) {

            // Coupon disables free shipping
            assertFalse(r.freeShipping,
                    "Coupon must disable free shipping");

            // Coupon blocks first purchase discount
            if (c.isFirstPurchase && !c.isGroceryItem) {
                assertTrue(r.discountPercentage <= 7.0,
                        "Coupon must block first purchase discount");
            }
        }
    }

    private void assertEUInvariants(PricingContext c, PricingResult r) {

        if (c.region == Region.EU) {
            assertTrue(r.discountPercentage <= 15.0,
                    "EU discount must be capped at 15%");
        }
    }

    private void assertFreeShippingInvariants(PricingContext c, PricingResult r) {

        if (r.freeShipping) {

            assertTrue(c.cartValue >= 100.0,
                    "Free shipping requires high cart value");

            assertFalse(c.hasCoupon,
                    "Free shipping must not apply with coupon");
        }
    }
}

