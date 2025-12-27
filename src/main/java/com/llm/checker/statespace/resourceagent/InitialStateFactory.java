package com.llm.checker.statespace.resourceagent;

import java.util.*;

public final class InitialStateFactory {

    public static SystemState createInitialState() {

        // --- Resources ---
        Map<Integer, ResourceState> resources = new HashMap<>();

        for (int r = 0; r < 3; r++) {
            ResourceState rs = new ResourceState();
            rs.status = ResourceStatus.FREE;
            rs.ownerAgentId = null;
            resources.put(r, rs);
        }

        // --- Agents ---
        Map<Integer, AgentState> agents = new HashMap<>();

        // Agent 0 requests resources {0,1}
        AgentState agent0 = new AgentState();
        agent0.status = AgentStatus.INIT;
        agent0.requestedResources = new HashSet<>(Arrays.asList(0, 1));
        agent0.heldReservations = new HashSet<>();
        agents.put(0, agent0);

        // Agent 1 requests resources {1,2}
        AgentState agent1 = new AgentState();
        agent1.status = AgentStatus.INIT;
        agent1.requestedResources = new HashSet<>(Arrays.asList(1, 2));
        agent1.heldReservations = new HashSet<>();
        agents.put(1, agent1);

        // --- System State ---
        SystemState initial = new SystemState();
        initial.resources = resources;
        initial.agents = agents;
        initial.steps = 0;

        return initial;
    }
}

