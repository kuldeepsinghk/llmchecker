package com.llm.checker.statespace.ecommpricing;

import java.util.ArrayList;
import java.util.List;

public class PricingStateGenerator {

    public static List<PricingContext> allStates() {

        List<PricingContext> states = new ArrayList<>();

        boolean[] bools = {true, false};
        double[] cartValues = {50.0, 100.0, 200.0};

        for (boolean premium : bools)
            for (boolean first : bools)
                for (boolean coupon : bools)
                    for (boolean grocery : bools)
                        for (Region region : Region.values())
                            for (double cart : cartValues) {

                                states.add(new PricingContext(
                                        premium,
                                        first,
                                        coupon,
                                        grocery,
                                        region,
                                        cart
                                ));
                            }

        return states;
    }
}

