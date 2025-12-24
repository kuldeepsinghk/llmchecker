package com.llm.checker.statespace;

import com.llm.checker.statespace.try1.TransitionGenerator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class StateExplorer {
    Set<SystemState> visited = new HashSet<>();
    static final int MAX_STEPS = 12;

    void explore(SystemState initial) {
        Deque<SystemState> stack = new ArrayDeque<>();
        stack.push(initial);

        while (!stack.isEmpty()) {
            SystemState current = stack.pop();
            // Bound the exploration depth
            if (current.steps > MAX_STEPS) {
                continue;
            }

            if (visited.contains(current)) continue;
            visited.add(current);

            InvariantChecker.check(current);

            for (Transition t : TransitionGenerator.allPossible(current)) {
                SystemState next = t.apply(current);
                if (next != null) {
                    stack.push(next);
                }
            }
        }
    }
}
