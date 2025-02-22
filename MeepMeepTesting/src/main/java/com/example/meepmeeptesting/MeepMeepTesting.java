package com.example.meepmeeptesting;

import com.example.auton.Auton;
import com.example.auton.AutonBasket;
import com.example.auton.AutonHuman;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        // Instantiate the appropriate auton depending on the run configuration
        // If you have a CLI argument of "blue", it will run the blue auton
        // And vice verse for red
        Auton auton;
        switch (args[0]) {
            case "basket":
                auton = new AutonBasket();
                break;
            case "human":
                auton = new AutonHuman();
                break;
            default:
                throw new RuntimeException("No Auton Specified! What am I supposed to simulate?");
        }
        // Set up MeepMeep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.525)
                .setDimensions(14.5, 14.5)
                // Make sure the robot has the appropriate starting position
                .setStartPose(auton.getStartingPose())
                .build();
        // Simulate the robot
        MeepMeepRunner runner = new MeepMeepRunner(myBot);
        auton.Run(runner, new MeepMeepGrabber(), new MeepMeepBeak(), new MeepMeepBucket());
        runner.Finish();
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}