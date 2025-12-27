package com.llm.checker.statespace.ecommpricing;

public class YourPricingServiceImplementation implements PricingService {
    @Override
    public PricingResult price(PricingContext context) {
        int discount=0;
        boolean freeShipping=false;
        if(!context.isGroceryItem){
            if(context.isPremiumCustomer){
                discount += 10;
            }
            if(context.isFirstPurchase && !context.hasCoupon){
               discount += 5;
            }
            if(context.hasCoupon){
                discount += 7;
            }
        }
        if(context.region == Region.EU){
            discount = Math.min(discount, 15);
        }
        if(context.cartValue >= 100 && !context.hasCoupon){
            freeShipping = true;
        }
        return new PricingResult(discount,freeShipping);
    }
}
