package com.llm.checker.statespace.ecommpricing;

import com.llm.checker.statespace.ecommpricing.naive.*;

import java.util.HashSet;
import java.util.Set;

public final class PricingExplorer {

    private PricingExplorer() {}

    public static Set<String> explore() {

        Set<String> states = new HashSet<>();
        NaivePricingEngine engine = new NaivePricingEngine();

        for (boolean premium : new boolean[]{true, false})
            for (boolean firstPurchase : new boolean[]{true, false})
                for (boolean hasCoupon : new boolean[]{true, false})
                    for (Region region : Region.values())
                        for (Category category : Category.values())
                            for (CartValue cartValue : CartValue.values()) {

                                PricingInput in = new PricingInput();
                                in.isPremium = premium;
                                in.isFirstPurchase = firstPurchase;
                                in.hasCoupon = hasCoupon;
                                in.region = region;
                                in.category = category;
                                in.cartValue = cartValue;

                                PricingState state = engine.apply(in);

                                // IMPORTANT CHANGE:
                                // We now record the FULL semantic state, not just final numbers
                                states.add(state.signature());
                            }

        return states;
    }
}


