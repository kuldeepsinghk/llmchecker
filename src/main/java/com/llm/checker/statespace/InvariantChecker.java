package com.llm.checker.statespace;

import java.util.HashMap;
import java.util.Map;

public class InvariantChecker {

    static void check(SystemState state) {

        // I1: Mutual Exclusion
        Map<Integer, Integer> committed = new HashMap<>();
        for (ResourceState r : state.resources.values()) {
            if (r.status == ResourceStatus.COMMITTED) {
                committed.merge(r.ownerAgentId, 1, Integer::sum);
            }
        }

        for (ResourceState r : state.resources.values()) {
            if (r.status == ResourceStatus.COMMITTED) {
                long count = state.resources.values().stream()
                        .filter(x -> x.status == ResourceStatus.COMMITTED &&
                                x.ownerAgentId.equals(r.ownerAgentId))
                        .count();
                if (count > r.ownerAgentId) {
                    throw new AssertionError("Mutual exclusion violated");
                }
            }
        }

        // I2: Reservation consistency
        for (ResourceState r : state.resources.values()) {
            if (r.status == ResourceStatus.RESERVED) {
                AgentState a = state.agents.get(r.ownerAgentId);
                if (a == null ||
                        !(a.status == AgentStatus.PREPARING ||
                                a.status == AgentStatus.COMMITTING)) {
                    throw new AssertionError("Invalid reservation");
                }
            }
        }

        // I3: Atomicity
        for (AgentState a : state.agents.values()) {
            if (a.status == AgentStatus.DONE) {
                for (Integer resId : a.requestedResources) {
                    ResourceState r = state.resources.get(resId);
                    if (r.status != ResourceStatus.COMMITTED ||
                            !r.ownerAgentId.equals(a)) {
                        throw new AssertionError("Atomicity violated");
                    }
                }
            }
        }
    }
}
