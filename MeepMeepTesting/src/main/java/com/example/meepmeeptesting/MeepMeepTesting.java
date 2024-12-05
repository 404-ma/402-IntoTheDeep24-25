package com.example.meepmeeptesting;

import com.example.auton.Auton;
import com.example.auton.AutonBlueHuman;
import com.example.auton.AutonRedBasket;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Objects;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        // Instantiate the appropriate auton depending on the run configuration
        // If you have a CLI argument of "blue", it will run the blue auton
        // And vice verse for red
        Auton auton;
        if(Objects.equals(args[0], "blue")){
            auton = new AutonBlueHuman();
        } else if (Objects.equals(args[0], "red")) {
            auton = new AutonRedBasket();
        }
        else{
            throw new RuntimeException("No Team Specified; Unable to start");
        }
        // Set up MeepMeep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.525)
                .setDimensions(14.5, 14.5)
                // Make sure the robot has the appropriate color and starting position
                .setColorScheme(auton.getClass() == AutonBlueHuman.class ? new ColorSchemeBlueDark() : new ColorSchemeRedDark())
                .setStartPose(auton.getStartingPose())
                .build();
        // Simulate the robot
        MeepMeepRunner runner = new MeepMeepRunner(myBot);
        auton.Run(runner);
        runner.Finish();
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}