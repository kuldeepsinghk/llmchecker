package com.llm.checker.statespace.ecommpricing;

public class PricingResult {
    public final double discountPercentage;
    public final boolean freeShipping;

    public PricingResult(double discountPercentage, boolean freeShipping) {
        this.discountPercentage = discountPercentage;
        this.freeShipping = freeShipping;
    }
}
