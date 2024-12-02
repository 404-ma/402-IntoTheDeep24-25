package com.example.auton;
// Implemented by classes that want to run Auton implementations
public interface Runner {
    // Every time this function is called, it will receive a lambda function as an argument
    // The lambda expects an argument of an action builder - the runner is responsible
    // for providing that. It will return the action builder with some new actions
    // added. The runner is then responsible for running it.
    void move(ActionFunction actionFunction);
}
