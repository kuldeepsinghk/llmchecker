package com.llm.checker.statespace.resourceagent;

public interface Transition {
    SystemState apply(SystemState state);
}
