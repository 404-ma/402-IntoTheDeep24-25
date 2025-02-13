package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;

// An interface that allows an auton to run in MeepMeep and on the robot
public interface Auton {
    // Equivalent to runOpMode - this is where you implement your opmode's logic
    void Run(Runner runner, IGrabber grabber, IBeak beak);

    // Returns the starting position of the robot
    Pose2d getStartingPose();
}