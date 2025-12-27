package com.llm.checker.statespace.ecommpricing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PricingInvariantTest {

    private final PricingService pricingService = new YourPricingServiceImplementation();

    @ParameterizedTest(name = "Basic sanity test #{index}")
    @MethodSource("allContexts")
    void pricingMustRespectBasicSanity(PricingContext context) {
        System.out.println("Testing with: " + contextToString(context));
        PricingResult result = pricingService.price(context);
        assertBasicSanity(result, context);
    }

    @ParameterizedTest(name = "Grocery invariant test #{index}")
    @MethodSource("allContexts")
    void pricingMustRespectGroceryInvariant(PricingContext context) {
        System.out.println("Testing with: " + contextToString(context));
        PricingResult result = pricingService.price(context);
        assertGroceryInvariants(context, result);
    }

    @ParameterizedTest(name = "Coupon invariant test #{index}")
    @MethodSource("allContexts")
    void pricingMustRespectCouponInvariant(PricingContext context) {
        System.out.println("Testing with: " + contextToString(context));
        PricingResult result = pricingService.price(context);
        assertCouponInvariants(context, result);
    }

    @ParameterizedTest(name = "EU invariant test #{index}")
    @MethodSource("allContexts")
    void pricingMustRespectEUInvariant(PricingContext context) {
        System.out.println("Testing with: " + contextToString(context));
        PricingResult result = pricingService.price(context);
        assertEUInvariants(context, result);
    }

    @ParameterizedTest(name = "Free shipping invariant test #{index}")
    @MethodSource("allContexts")
    void pricingMustRespectFreeShippingInvariant(PricingContext context) {
        System.out.println("Testing with: " + contextToString(context));
        PricingResult result = pricingService.price(context);
        assertFreeShippingInvariants(context, result);
    }

    private void assertBasicSanity(PricingResult r, PricingContext context) {
        assertTrue(r.discountPercentage >= 0.0,
                "Discount must not be negative for " + contextToString(context));

        assertTrue(r.discountPercentage <= 100.0,
                "Discount must not exceed 100% for " + contextToString(context));
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
                    "Coupon must disable free shipping for " + contextToString(c));

            // Check that first purchase discount doesn't get applied
            if (c.isFirstPurchase && !c.isGroceryItem) {
                // Calculate what the discount would be without the 5% first purchase discount
                double expectedDiscount = 0;
                if (c.isPremiumCustomer) expectedDiscount += 10;
                expectedDiscount += 7; // Coupon discount

                // Apply EU cap if needed
                if (c.region == Region.EU) {
                    expectedDiscount = Math.min(expectedDiscount, 15.0);
                }

                assertEquals(expectedDiscount, r.discountPercentage, 0.01,
                        "With coupon, first purchase discount must not be applied for " + contextToString(c));
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

    // Helper to format context for error messages
    private String contextToString(PricingContext c) {
        return String.format("Context{Premium=%b, First=%b, Coupon=%b, Grocery=%b, Region=%s, CartValue=%.2f}",
                c.isPremiumCustomer, c.isFirstPurchase, c.hasCoupon, c.isGroceryItem, c.region, c.cartValue);
    }

    // Method source that provides the contexts one by one
    static Stream<PricingContext> allContexts() {
        return PricingStateGenerator.allStates().stream();
    }
}

