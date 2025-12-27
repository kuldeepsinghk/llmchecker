package com.llm.checker.statespace.resourceagent;

public class LLMStateCheckerMain {
    public static void main(String[] args) {

        // 1. Create initial system state
        SystemState initial = InitialStateFactory.createInitialState();

        // 2. Create explorer
        StateExplorer explorer = new StateExplorer();

        // 3. Run exhaustive exploration
        explorer.explore(initial);

        // 4. If we reach here, the test PASSED
        System.out.println("TEST PASSED: No invariant violations found.");
    }
}
