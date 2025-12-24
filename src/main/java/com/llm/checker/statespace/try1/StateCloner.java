package com.llm.checker.statespace.try1;

import com.llm.checker.statespace.AgentState;
import com.llm.checker.statespace.ResourceState;
import com.llm.checker.statespace.SystemState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class StateCloner {

    static SystemState clone(SystemState s) {
        SystemState ns = new SystemState();

        ns.resources = new HashMap<>();
        for (Map.Entry<Integer, ResourceState> e : s.resources.entrySet()) {
            ResourceState r = new ResourceState();
            r.status = e.getValue().status;
            r.ownerAgentId = e.getValue().ownerAgentId;
            ns.resources.put(e.getKey(), r);
            ns.steps=s.steps+1;
        }

        ns.agents = new HashMap<>();
        for (Map.Entry<Integer, AgentState> e : s.agents.entrySet()) {
            AgentState a = new AgentState();
            a.status = e.getValue().status;
            a.requestedResources = new HashSet<>(e.getValue().requestedResources);
            a.heldReservations = new HashSet<>(e.getValue().heldReservations);
            ns.agents.put(e.getKey(), a);
        }

        return ns;
    }
}

