package com.llm.checker.statespace;

import java.util.Set;

public class AgentState {
    public AgentStatus status;
    public Set<Integer> requestedResources;
    public Set<Integer> heldReservations;
}
