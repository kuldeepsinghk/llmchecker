package com.llm.checker.statespace.try1;
import com.llm.checker.statespace.*;

class Retry implements Transition {
    private final int agentId;

    Retry(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);
        if (a.status != AgentStatus.ABORTED) return null;

        SystemState ns = StateCloner.clone(s);
        ns.agents.get(agentId).status = AgentStatus.INIT;
        ns.agents.get(agentId).heldReservations.clear();

        return ns;
    }
}

