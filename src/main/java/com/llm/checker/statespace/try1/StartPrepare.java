package com.llm.checker.statespace.try1;

import com.llm.checker.statespace.AgentState;
import com.llm.checker.statespace.AgentStatus;
import com.llm.checker.statespace.SystemState;
import com.llm.checker.statespace.Transition;

class StartPrepare implements Transition {
    private final int agentId;

    StartPrepare(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);
        if (a.status != AgentStatus.INIT) return null;

        SystemState ns = StateCloner.clone(s);
        ns.agents.get(agentId).status = AgentStatus.PREPARING;
        return ns;
    }
}

