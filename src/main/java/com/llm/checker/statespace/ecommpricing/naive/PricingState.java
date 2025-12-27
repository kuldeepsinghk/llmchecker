package com.llm.checker.statespace.ecommpricing.naive;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the full semantic state of pricing evaluation.
 * This intentionally captures "what happened", not just "what came out".
 */
public class PricingState {

    /** Captures outcome of every rule */
    private final Map<Rule, RuleOutcome> ruleOutcomes =
            new EnumMap<>(Rule.class);

    /** Final computed values */
    private int discountAmount;
    private int shippingCost;

    public PricingState() {
        // Initialize all rules explicitly
        for (Rule r : Rule.values()) {
            ruleOutcomes.put(r, RuleOutcome.NOT_APPLICABLE);
        }
    }

    /* ---------------- Rule Outcome API ---------------- */

    public void mark(Rule rule, RuleOutcome outcome) {
        ruleOutcomes.put(rule, outcome);
    }

    public Map<Rule, RuleOutcome> ruleOutcomes() {
        return Collections.unmodifiableMap(ruleOutcomes);
    }

    /* ---------------- Result Fields ---------------- */

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(int shippingCost) {
        this.shippingCost = shippingCost;
    }

    /* ---------------- State Signature ---------------- */

    /**
     * Semantic signature used by the test harness.
     * Two states are different if their reasoning differs,
     * even if final prices are identical.
     */
    public String signature() {
        return ruleOutcomes.toString()
                + "|discount=" + discountAmount
                + "|shipping=" + shippingCost;
    }

    /* ---------------- Equality (Optional but Useful) ---------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PricingState)) return false;
        PricingState that = (PricingState) o;
        return discountAmount == that.discountAmount &&
                shippingCost == that.shippingCost &&
                ruleOutcomes.equals(that.ruleOutcomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleOutcomes, discountAmount, shippingCost);
    }

    @Override
    public String toString() {
        return signature();
    }
}
