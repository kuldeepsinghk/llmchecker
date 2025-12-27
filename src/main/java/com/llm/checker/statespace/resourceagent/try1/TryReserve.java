package com.llm.checker.statespace.resourceagent.try1;

import com.llm.checker.statespace.resourceagent.*;

class TryReserve implements Transition {
    private final int agentId;
    private final int resourceId;

    TryReserve(int agentId, int resourceId) {
        this.agentId = agentId;
        this.resourceId = resourceId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);
        ResourceState r = s.resources.get(resourceId);

        if (a.status != AgentStatus.PREPARING) return null;
        if (!a.requestedResources.contains(resourceId)) return null;
        if (r.status != ResourceStatus.FREE) return null;

        SystemState ns = StateCloner.clone(s);
        ns.resources.get(resourceId).status = ResourceStatus.RESERVED;
        ns.resources.get(resourceId).ownerAgentId = agentId;
        ns.agents.get(agentId).heldReservations.add(resourceId);

        return ns;
    }
}

