package com.llm.checker.statespace;

public interface Transition {
    SystemState apply(SystemState state);
}
