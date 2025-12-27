package com.llm.checker.statespace.ecommpricing;

import java.util.Set;

public class EcommPriceMain {
    public static void main(String[] args) {
        Set<String> states = PricingExplorer.explore();
        System.out.println("Naive pricing states: " + states.size());
    }
}
