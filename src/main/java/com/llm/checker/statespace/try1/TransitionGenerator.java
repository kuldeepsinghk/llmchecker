package com.llm.checker.statespace.try1;

import com.llm.checker.statespace.SystemState;
import com.llm.checker.statespace.Transition;

import java.util.ArrayList;
import java.util.List;

public class TransitionGenerator {
    public static List<Transition> allPossible(SystemState state) {
        List<Transition> transitions = new ArrayList<>();

        for (Integer agentId : state.agents.keySet()) {
            transitions.add(new StartPrepare(agentId));
            transitions.add(new Commit(agentId));
            transitions.add(new Rollback(agentId));
            transitions.add(new Crash(agentId));
            transitions.add(new Retry(agentId));

            for (Integer resId : state.resources.keySet()) {
                transitions.add(new TryReserve(agentId, resId));
            }
        }

        return transitions;
    }
}
