package com.example.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

@FunctionalInterface
public interface ActionFunction {
    TrajectoryActionBuilder evaluate(TrajectoryActionBuilder builder);
}
