package com.example.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

@FunctionalInterface
// Signature of the lambda function you have to pass to runAction()
public interface ActionFunction {
    TrajectoryActionBuilder evaluate(TrajectoryActionBuilder builder);
}
