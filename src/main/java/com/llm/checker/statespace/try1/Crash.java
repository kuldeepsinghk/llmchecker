package com.llm.checker.statespace.try1;
import com.llm.checker.statespace.*;

class Crash implements Transition {
    private final int agentId;

    Crash(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public SystemState apply(SystemState s) {
        AgentState a = s.agents.get(agentId);
        if (a.status == AgentStatus.DONE) return null;

        SystemState ns = StateCloner.clone(s);
        ns.agents.get(agentId).status = AgentStatus.ABORTED;
        return ns;
    }
}

