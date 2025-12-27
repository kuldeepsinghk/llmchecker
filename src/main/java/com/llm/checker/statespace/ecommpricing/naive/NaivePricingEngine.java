package com.llm.checker.statespace.ecommpricing.naive;

public class NaivePricingEngine {

    public PricingState apply(PricingInput in) {

        PricingState s = new PricingState();

        int discount = 0;

        /* ---------------- Rule 1: Premium Discount ---------------- */
        if (in.isPremium) {
            if (in.category == Category.GROCERY) {
                s.mark(
                        Rule.PREMIUM_DISCOUNT,
                        RuleOutcome.BLOCKED
                );
            } else {
                discount += 10;
                s.mark(
                        Rule.PREMIUM_DISCOUNT,
                        RuleOutcome.APPLIED
                );
            }
        } else {
            s.mark(
                    Rule.PREMIUM_DISCOUNT,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Rule 2: First Purchase Discount ---------------- */
        if (in.isFirstPurchase) {
            if (in.hasCoupon) {
                s.mark(
                        Rule.FIRST_PURCHASE_DISCOUNT,
                        RuleOutcome.SUPPRESSED
                );
            } else {
                discount += 5;
                s.mark(
                        Rule.FIRST_PURCHASE_DISCOUNT,
                        RuleOutcome.APPLIED
                );
            }
        } else {
            s.mark(
                    Rule.FIRST_PURCHASE_DISCOUNT,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Rule 3: Coupon Discount ---------------- */
        if (in.hasCoupon) {
            discount += 7;
            s.mark(
                    Rule.COUPON_DISCOUNT,
                    RuleOutcome.APPLIED
            );
        } else {
            s.mark(
                    Rule.COUPON_DISCOUNT,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Rule 4: Grocery Blocks Percentage Discounts ---------------- */
        if (in.category == Category.GROCERY) {
            if (discount > 0) {
                discount = 0;
                s.mark(
                        Rule.GROCERY_BLOCK_PERCENTAGE,
                        RuleOutcome.APPLIED
                );

                // Mark overridden rules
                overrideIfApplied(s, Rule.PREMIUM_DISCOUNT);
                overrideIfApplied(s, Rule.FIRST_PURCHASE_DISCOUNT);
                overrideIfApplied(s, Rule.COUPON_DISCOUNT);
            } else {
                s.mark(
                        Rule.GROCERY_BLOCK_PERCENTAGE,
                        RuleOutcome.NOT_APPLICABLE
                );
            }
        } else {
            s.mark(
                    Rule.GROCERY_BLOCK_PERCENTAGE,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Rule 5: EU Discount Cap ---------------- */
        if (in.region == Region.EU && discount > 15) {
            discount = 15;
            s.mark(
                    Rule.EU_DISCOUNT_CAP,
                    RuleOutcome.APPLIED
            );
        } else if (in.region == Region.EU) {
            s.mark(
                    Rule.EU_DISCOUNT_CAP,
                    RuleOutcome.NOT_APPLICABLE
            );
        } else {
            s.mark(
                    Rule.EU_DISCOUNT_CAP,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Rule 6: Free Shipping ---------------- */
        if (in.cartValue == CartValue.HIGH && !in.hasCoupon) {
            s.setShippingCost(0);
            s.mark(
                    Rule.FREE_SHIPPING_RULE,
                    RuleOutcome.APPLIED
            );
        } else {
            s.setShippingCost(10);
            s.mark(
                    Rule.FREE_SHIPPING_RULE,
                    RuleOutcome.NOT_APPLICABLE
            );
        }

        /* ---------------- Finalize ---------------- */
        s.setDiscountAmount(discount);

        return s;
    }

    /* ------------------------------------------------------------
       Helper: override rule outcome if it was previously applied
       ------------------------------------------------------------ */
    private void overrideIfApplied(
            PricingState state,
            Rule rule
    ) {
        RuleOutcome current =
                state.ruleOutcomes().get(rule);

        if (current == RuleOutcome.APPLIED) {
            state.mark(rule, RuleOutcome.OVERRIDDEN);
        }
    }
}
