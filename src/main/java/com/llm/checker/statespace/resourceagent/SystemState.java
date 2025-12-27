package com.llm.checker.statespace.resourceagent;

import java.util.Map;
import java.util.Objects;

public class SystemState {
   public  Map<Integer, ResourceState> resources;
   public Map<Integer, AgentState> agents;


   // Introduce bounded context for state space
   public int steps;


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof SystemState that)) return false;
      return steps == that.steps && Objects.equals(resources, that.resources) && Objects.equals(agents, that.agents);
   }

   @Override
   public int hashCode() {
      return Objects.hash(resources, agents, steps);
   }
}

