package com.llm.checker.statespace.ecommpricing;

public class YourPricingServiceImplementation implements PricingService {
    @Override
    public PricingResult price(PricingContext context) {
        return new PricingResult(10,false);
    }
}
