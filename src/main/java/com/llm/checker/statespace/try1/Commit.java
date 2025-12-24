package com.llm.checker.statespace.try1;
import com.llm.checker.statespace.*;

class Commit implements Transition {
    private final int agentId;

    Commit(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);

        if (a.status != AgentStatus.PREPARING) return null;
        if (!a.heldReservations.containsAll(a.requestedResources)) return null;

        SystemState ns = StateCloner.clone(s);

        for (Integer resId : a.requestedResources) {
            ResourceState r = ns.resources.get(resId);
            r.status = ResourceStatus.COMMITTED;
            r.ownerAgentId = agentId;
        }

        ns.agents.get(agentId).status = AgentStatus.DONE;
        ns.agents.get(agentId).heldReservations.clear();

        return ns;
    }
}

