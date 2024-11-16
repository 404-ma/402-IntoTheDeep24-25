package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;

public interface Auton {
    void Run(Runner runner);
    Pose2d getStartingPose();
}