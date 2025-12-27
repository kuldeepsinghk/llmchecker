package com.llm.checker.statespace.resourceagent.try1;

import com.llm.checker.statespace.resourceagent.*;


class Rollback implements Transition {
    private final int agentId;

    Rollback(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);
        if (a.heldReservations.isEmpty()) return null;

        SystemState ns = StateCloner.clone(s);

        for (Integer resId : a.heldReservations) {
            ResourceState r = ns.resources.get(resId);
            r.status = ResourceStatus.FREE;
            r.ownerAgentId = null;
        }

        ns.agents.get(agentId).heldReservations.clear();
        ns.agents.get(agentId).status = AgentStatus.ABORTED;

        return ns;
    }
}

