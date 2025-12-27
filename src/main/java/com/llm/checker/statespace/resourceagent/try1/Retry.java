package com.llm.checker.statespace.resourceagent.try1;
import com.llm.checker.statespace.resourceagent.AgentState;
import com.llm.checker.statespace.resourceagent.AgentStatus;
import com.llm.checker.statespace.resourceagent.SystemState;
import com.llm.checker.statespace.resourceagent.Transition;

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

